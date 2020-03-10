import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import Tooltip from '@material-ui/core/Tooltip';
import DragIndicatorIcon from '@material-ui/icons/DragIndicator';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import IconButton from '@material-ui/core/IconButton';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import NavButton from '../../components/navigation/NavButton';
import NoRecordsFound from '../../components/NoRecordsFound';
import MenuService from '../../services/MenuService';

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
  dragIndicator: {
    color: 'grey',
    cursor: 'move',
    width: '30px',
    height: '30px'
  }
});

function Category({ match }) {
  const { restaurantId, menuId } = useParams();
  const [categories, setCategories] = useState(null);
  const classes = useStyles();
  let history = useHistory();

  useEffect(() => {
    MenuService.getCategoriesByMenuId(menuId).then(categories => {
      setCategories(categories);
    });
  }, []);

  const handleAdd = () => {};

  const handleEdit = () => {};

  const handleNewCategory = () => {};

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

    const newCategories = Object.assign([], categories);
    const category = categories[source.index];
    newCategories.splice(source.index, 1);
    newCategories.splice(destination.index, 0, category);
    setCategories(newCategories);
  };

  return (
    <>
      <NavButton
        previousURL={`/restaurant/${restaurantId}/menu`}
        buttonTitle="Menus"
      />
      <br />
      <br />

      {categories && categories.length === 0 && (
        <NoRecordsFound
          message="Você não tem nenhuma categoria cadastrada para esse menu ainda. Cadastre agora mesmo."
          buttonTitle="Cadastrar Categoria"
          onButtonClick={handleNewCategory}
        />
      )}
      {categories && categories.length > 0 && (
        <Grid container direction="column">
          <Typography
            style={{
              textTransform: 'none'
            }}
            variant="h5"
          >
            Categorias
          </Typography>
          <Grid item xs={12}>
            <DragDropContext onDragEnd={onDragEnd}>
              {categories.map((category, index) => (
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
                              <CardContent
                                className={classes.cardContent}
                                onClick={() =>
                                  history.push(
                                    `${match.url}/${category.id}/product`
                                  )
                                }
                              >
                                <Grid container>
                                  <Grid item style={{ marginRight: '20px' }}>
                                    <DragIndicatorIcon
                                      className={classes.dragIndicator}
                                    />
                                  </Grid>
                                  <Grid item xs={5}>
                                    <Typography variant="h6" component="h2">
                                      {category.name}
                                    </Typography>
                                  </Grid>
                                </Grid>
                              </CardContent>
                              <CardActions className={classes.cardActions}>
                                <Tooltip title="Atualizar categoria">
                                  <IconButton onClick={() => handleEdit()}>
                                    <EditIcon />
                                  </IconButton>
                                </Tooltip>

                                <Tooltip title="Remover categoria">
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
        </Grid>
      )}
    </>
  );
}

export default Category;
