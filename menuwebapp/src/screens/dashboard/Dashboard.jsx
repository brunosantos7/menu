import React, { useState, useEffect } from "react";
import Grid from "@material-ui/core/Grid";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import NoRecordsFound from "../../components/NoRecordsFound";
import Restaurant from "../restaurant/Restaurant";
import UserService from "../../services/UserService";

export default function Dashboard() {
  const hardCodedUserId = 2;
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    // UserService.authenticate("bruno.santos7@outlook.com", "testando").then(
    //   response => {
    //     console.log(response);
    //   }
    // );

    UserService.getRestaurantsByUserId(hardCodedUserId).then(restaurants => {
      setRestaurants(restaurants);
    });
  }, []);

  const handleNewRestaurant = () => {};

  return (
    <>
      {restaurants.length == 0 && (
        <NoRecordsFound
          message="Bem-vindo ao EatUp. Parece que você não tem nenhum restaurante cadastrado. Não perca mais tempo e disponibilize o seu cardapio online para seus clientes. É de graça!"
          buttonTitle="Cadastrar Restaurante"
          onButtonClick={handleNewRestaurant}
        />
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
