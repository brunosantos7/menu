import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import NavButton from '../../components/navigation/NavButton';
import NoRecordsFound from '../../components/NoRecordsFound';
import RestaurantService from '../../services/RestaurantService';

const useStyles = makeStyles({
  root: {
    margin: '20px 0px'
  },
  cardContent: {
    backgroundColor: '#efefef',
    cursor: 'pointer'
  },
  cardActions: {
    height: '50px'
  }
});

function Menu({ match }) {
  const { restaurantId } = useParams();
  const [menus, setMenus] = useState(null);
  const classes = useStyles();
  let history = useHistory();

  useEffect(() => {
    RestaurantService.getMenusByRestaurantId(restaurantId).then(menus => {
      setMenus(menus);
    });
  }, []);

  const handleEdit = () => {};

  const handleDelete = () => {};

  const handleNewCategory = () => {};

  return (
    <>
      <NavButton previousURL="/" buttonTitle="Restaurantes" />
      <br />
      <br />

      {menus && menus.length === 0 && (
        <NoRecordsFound
          message="Você não tem nenhum menu cadastrado para esse restaurante ainda."
          buttonTitle="Cadastrar Menu"
          onButtonClick={handleNewCategory}
        />
      )}

      {menus && menus.length > 0 && (
        <Grid container direction="column">
          <Typography
            style={{
              textTransform: 'none'
            }}
            variant="h5"
          >
            Menu
          </Typography>
          <Grid item xs={12}>
            {menus.map(menu => (
              <Card key={menu.id} className={classes.root}>
                <CardContent
                  className={classes.cardContent}
                  onClick={() =>
                    history.push(`${match.url}/${menu.id}/category`)
                  }
                >
                  <Typography variant="h6" component="h2">
                    {menu.title}
                  </Typography>
                </CardContent>
                <CardActions className={classes.cardActions}>
                  <Tooltip title="Atualizar menu">
                    <IconButton onClick={() => handleEdit()}>
                      <EditIcon />
                    </IconButton>
                  </Tooltip>

                  <Tooltip title="Remover menu">
                    <IconButton onClick={() => handleDelete()}>
                      <DeleteIcon />
                    </IconButton>
                  </Tooltip>
                </CardActions>
              </Card>
            ))}
          </Grid>
        </Grid>
      )}
    </>
  );
}

export default Menu;
