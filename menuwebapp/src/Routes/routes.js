import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import RestaurantForm from "../screens/restaurant/RestaurantForm";
import UserProfileForm from "../screens/userprofile/UserProfileForm";
import Dashboard from "../screens/dashboard/Dashboard";

export default function Routes() {
  return (
    <Switch>
      <Route exact path="/" component={Dashboard} />
      <Route path="/profile" component={UserProfileForm} />
      <Route path="/restaurant" component={RestaurantForm} />
    </Switch>
  );
}
