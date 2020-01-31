import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
export default function Dashboard() {
  const classes = useStyles();

  return (
    <>
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
          style={{ fontSize: 30, color: "grey" }}
        >
          Bem-vindo ao EatUp. Parece que voce nao tem nenhum restaurante
          cadastrado. Nao perca mais tempo e disponibilize o seu cardapio online
          para seus clientes. DE GRACA!! =)
        </Grid>
        <Grid item xs={12} md={8} lg={9}>
          <Link
            to="/restaurant"
            style={{ textDecoration: "none", color: "white" }}
          >
            <Button variant="contained" color="primary">
              Cadastre-se Agora
            </Button>
          </Link>
        </Grid>
      </Grid>
    </>
  );
}

const useStyles = makeStyles(theme => ({
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4)
  },
  paper: {
    padding: theme.spacing(2),
    display: "flex",
    overflow: "auto",
    flexDirection: "column"
  },
  fixedHeight: {
    height: 240
  }
}));
