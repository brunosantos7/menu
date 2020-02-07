import React, { useState, useEffect } from 'react';
import Grid from '@material-ui/core/Grid';
import { Link } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import Restaurant from '../restaurant/Restaurant';
import UserService from '../../services/UserService';

export default function Dashboard() {
  const hardCodedUserId = 1;
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    UserService.getRestaurantsByUserId(hardCodedUserId).then(restaurants => {
      setRestaurants(restaurants);
    });
  }, []);

  return (
    <>
      {restaurants.length == 0 && (
        <Grid
          container
          spacing={3}
          direction="column"
          justify="center"
          alignItems="center"
        >
          <Grid
            item
            xs={12}
            md={8}
            lg={9}
            style={{ fontSize: 30, color: 'grey' }}
          >
            Bem-vindo ao EatUp. Parece que voce nao tem nenhum restaurante
            cadastrado. Nao perca mais tempo e disponibilize o seu cardapio
            online para seus clientes. DE GRACA!! =)
          </Grid>
          <Grid item xs={12} md={8} lg={9}>
            <Link
              to="/restaurant"
              style={{ textDecoration: 'none', color: 'white' }}
            >
              <Button variant="contained" color="primary">
                Cadastre-se Agora
              </Button>
            </Link>
          </Grid>
        </Grid>
      )}

      {restaurants.length > 0 && (
        <Grid container direction="column">
          <Grid item xs={12}>
            <Restaurant restaurants={restaurants} />
          </Grid>
        </Grid>
      )}
    </>
  );
}
