import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import AddIcon from '@material-ui/icons/AddCircleOutline';

const tileWidth = '400px';
const tileHeight = '250px';

const useStyles = makeStyles({
  editIcon: {
    color: 'rgba(255, 255, 255, 0.54)'
  },
  button: {
    width: tileWidth,
    height: tileHeight,
    backgroundColor: '#efefef'
  },
  buttonIcon: {
    fontSize: '32px',
    marginBottom: '8px',
    color: 'grey'
  },
  buttonLabel: {
    flexDirection: 'column',
    marginLeft: '-20px'
  }
});

function Restaurant({ restaurants }) {
  const classes = useStyles();

  return (
    <>
      <GridList spacing={20}>
        <GridListTile
          style={{
            width: tileWidth,
            height: tileHeight
          }}
        >
          <Button
            classes={{ root: classes.button, label: classes.buttonLabel }}
          >
            <AddIcon className={classes.buttonIcon} />
            Cadastrar Restaurante
          </Button>
        </GridListTile>
        {restaurants.map(tile => (
          <GridListTile
            style={{
              width: tileWidth,
              height: tileHeight
            }}
            key={tile.id}
          >
            <img src={tile.uri} alt={tile.name} />
            <GridListTileBar
              title={tile.name}
              subtitle={<span>{`${tile.street}, ${tile.number}`}</span>}
              actionIcon={
                <IconButton className={classes.editIcon}>
                  <EditIcon />
                </IconButton>
              }
            />
          </GridListTile>
        ))}
      </GridList>
    </>
  );
}

export default Restaurant;
