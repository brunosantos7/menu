import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/AddCircleRounded';
import EditIcon from '@material-ui/icons/Edit';
import Chip from '@material-ui/core/Chip';
import NavButton from '../../components/navigation/NavButton';
import NoRecordsFound from '../../components/NoRecordsFound';
import MenuService from '../../services/MenuService';

const useStyles = makeStyles({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: 5
    }
  }
});

function Category({ match }) {
  const { restaurantId, menuId } = useParams();
  const [categories, setCategories] = useState([]);
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

  return (
    <>
      <NavButton
        previousURL={`/restaurant/${restaurantId}/menu`}
        buttonTitle="Menus"
      />
      <br />
      <br />

      {categories.length === 0 && (
        <NoRecordsFound
          message="Você não tem nenhuma categoria cadastrada para esse menu ainda. Cadastre agora mesmo."
          buttonTitle="Cadastrar Categoria"
          onButtonClick={handleNewCategory}
        />
      )}
      {categories.length > 0 && (
        <div className={classes.root}>
          <Chip
            label="Cadastrar Categoria"
            variant="outlined"
            onClick={handleAdd}
            onDelete={handleAdd}
            deleteIcon={<AddIcon />}
          />

          {categories.map(category => (
            <Chip
              key={category.id}
              label={category.name}
              onClick={() =>
                history.push(`${match.url}/${category.id}/product`)
              }
              onDelete={handleEdit}
              deleteIcon={<EditIcon />}
            />
          ))}
        </div>
      )}
    </>
  );
}

export default Category;
