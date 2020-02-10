import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import NavButton from '../../components/navigation/NavButton';
import AddIcon from '@material-ui/icons/AddCircleRounded';
import NoRecordsFound from '../../components/NoRecordsFound';
import CategoryService from '../../services/CategoryService';

const tileWidth = '300px';
const tileHeight = '300px';

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

function Product() {
  const { restaurantId, menuId, categoryId } = useParams();
  const [products, setProducts] = useState([]);
  const classes = useStyles();

  useEffect(() => {
    CategoryService.getProductsByCategoryId(categoryId).then(products => {
      setProducts(products);
    });
  }, []);

  const handleNewCategory = () => {};

  const handleEdit = () => {};

  return (
    <>
      <NavButton
        previousURL={`/restaurant/${restaurantId}/menu/${menuId}/category`}
        buttonTitle="Categorias"
      />
      <br />
      <br />

      {products.length === 0 && (
        <NoRecordsFound
          message="Você não tem nenhum produto cadastrado para essa categoria ainda."
          buttonTitle="Cadastrar Produto"
          onButtonClick={handleNewCategory}
        />
      )}

      {products.length > 0 && (
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
              Cadastrar Produto
            </Button>
          </GridListTile>

          {products.map(product => (
            <GridListTile
              style={{
                width: tileWidth,
                height: tileHeight
              }}
              key={product.id}
            >
              <img src={product.imageUri} />
              <GridListTileBar
                title={product.name}
                subtitle={
                  <>
                    <span>{product.description}</span>
                    <br />
                    <br />
                    <span>R$ 48,90</span>
                  </>
                }
                actionIcon={
                  <IconButton className={classes.editIcon} onClick={handleEdit}>
                    <EditIcon />
                  </IconButton>
                }
              />
            </GridListTile>
          ))}
        </GridList>
      )}
    </>
  );
}

export default Product;
