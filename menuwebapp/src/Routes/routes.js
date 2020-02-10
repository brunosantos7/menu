import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import UserProfileForm from '../screens/userprofile/UserProfileForm';
import Dashboard from '../screens/dashboard/Dashboard';
import Menu from '../screens/menu/Menu';
import Category from '../screens/category/Category';
import Product from '../screens/product/Product';

export default function Routes() {
  return (
    <Switch>
      <Route exact path="/" component={Dashboard} />
      <Route path="/profile" component={UserProfileForm} />
      <Route exact path="/restaurant/:restaurantId/menu" component={Menu} />
      <Route
        exact
        path="/restaurant/:restaurantId/menu/:menuId/category"
        component={Category}
      />
      <Route
        exact
        path="/restaurant/:restaurantId/menu/:menuId/category/:categoryId/Product"
        component={Product}
      />
    </Switch>
  );
}
