import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { makeStyles } from '@material-ui/core/styles';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import Typography from '@material-ui/core/Typography';
import Tooltip from '@material-ui/core/Tooltip';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import DragIndicatorIcon from '@material-ui/icons/DragIndicator';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import NavButton from '../../components/navigation/NavButton';
import NoRecordsFound from '../../components/NoRecordsFound';
import CategoryService from '../../services/CategoryService';

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
  },
  cardMedia: {
    width: '100px',
    height: '100px'
  },
  dragIndicator: {
    color: 'grey',
    cursor: 'move',
    width: '30px',
    height: '30px'
  }
});

function Product() {
  const { restaurantId, menuId, categoryId } = useParams();
  const [products, setProducts] = useState(null);
  const classes = useStyles();

  useEffect(() => {
    CategoryService.getProductsByCategoryId(categoryId).then(products => {
      setProducts(products);
    });
  }, []);

  const handleNewCategory = () => {};

  const handleEdit = () => {};

  const onDragEnd = ({ source, destination }) => {
    if (!destination) {
      return;
    }

    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }

    const newProducts = Object.assign([], products);
    const category = products[source.index];
    newProducts.splice(source.index, 1);
    newProducts.splice(destination.index, 0, category);
    setProducts(newProducts);
  };

  return (
    <>
      <NavButton
        previousURL={`/restaurant/${restaurantId}/menu/${menuId}/category`}
        buttonTitle="Categorias"
      />
      <br />
      <br />

      {products && products.length === 0 && (
        <NoRecordsFound
          message="Você não tem nenhum produto cadastrado para essa categoria ainda."
          buttonTitle="Cadastrar Produto"
          onButtonClick={handleNewCategory}
        />
      )}

      {products && products.length > 0 && (
        <>
          <Typography
            style={{
              textTransform: 'none'
            }}
            variant="h5"
          >
            Produtos
          </Typography>

          <Grid item xs={12}>
            <DragDropContext onDragEnd={onDragEnd}>
              {products.map((product, index) => (
                <Droppable droppableId={index.toString()} key={index}>
                  {provided => (
                    <div ref={provided.innerRef} {...provided.droppableProps}>
                      <Draggable draggableId={index.toString()} index={index}>
                        {provided => (
                          <div
                            ref={provided.innerRef}
                            {...provided.draggableProps}
                            {...provided.dragHandleProps}
                          >
                            <Card className={classes.root}>
                              <CardContent className={classes.cardContent}>
                                <Grid container>
                                  <Grid item style={{ marginRight: '20px' }}>
                                    <DragIndicatorIcon
                                      className={classes.dragIndicator}
                                    />
                                  </Grid>
                                  <Grid item style={{ marginRight: '20px' }}>
                                    <CardMedia
                                      className={classes.cardMedia}
                                      image={product.imageUri}
                                      title={product.name}
                                    />
                                  </Grid>
                                  <Grid item md={8} lg={9}>
                                    <Typography variant="h6">
                                      {product.name}
                                    </Typography>
                                    <br />
                                    <span>{product.description}</span>
                                    <br />
                                    <span>{`R$ ${product.price}`}</span>
                                  </Grid>
                                </Grid>
                              </CardContent>
                              <CardActions className={classes.cardActions}>
                                <Tooltip title="Atualizar produto">
                                  <IconButton onClick={() => handleEdit()}>
                                    <EditIcon />
                                  </IconButton>
                                </Tooltip>

                                <Tooltip title="Remover produto">
                                  <IconButton onClick={() => handleEdit()}>
                                    <DeleteIcon />
                                  </IconButton>
                                </Tooltip>
                              </CardActions>
                            </Card>
                          </div>
                        )}
                      </Draggable>
                      {provided.placeholder}
                    </div>
                  )}
                </Droppable>
              ))}
            </DragDropContext>
          </Grid>
        </>
      )}
    </>
  );
}

export default Product;
