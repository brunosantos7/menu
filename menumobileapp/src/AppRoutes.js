import React from 'react';
import { createAppContainer, createSwitchNavigator } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import LoadingPage from './pages/LoadingPage';
import Home from './pages/Home';
import CategoryList from './pages/CategoryList';
import ProductList from './pages/ProductList';
import ProductDetail from './pages/ProductDetail';
import MnBackButton from './components/MnBackButton';
import { colors } from './constants';

const headerStyle = {
    backgroundColor: colors.white,
};

const headerTitleStyle = {
    fontSize: 18,
    fontFamily: 'OpenSans-Regular',
    color: colors.primary,
};

const defaultNavigationOptions = {
    headerStyle: headerStyle,
    headerTitleStyle: headerTitleStyle,
};

const AuthLoadingNavigator = createStackNavigator(
    {
        LoadingPage: {
            screen: LoadingPage,
            navigationOptions: (navigation) => ({
                header: () => <></>
            }),
        }
    },
    {
        initialRouteName: 'LoadingPage',
        defaultNavigationOptions: defaultNavigationOptions,
        headerTitleAlign: 'center',
    },
);

const AppNavigator = createStackNavigator(
    {
        Home: {
            screen: Home,
            navigationOptions: (navigation) => ({
                // title: navigation.navigation.getParam('title', 'Menu'),
                header: () => <></>,
            }),
        },
        CategoryList: {
            screen: CategoryList,
            navigationOptions: (navigation) => ({
                title: navigation.navigation.getParam('title', 'Categorias'),
                headerLeft: () => <MnBackButton />,
            }),
        },
        ProductList: {
            screen: ProductList,
            navigationOptions: (navigation) => ({
                title: navigation.navigation.getParam('title', 'Produtos'),
                headerLeft: () => <MnBackButton />,
            }),
        },
        ProductDetail: {
            screen: ProductDetail,
            navigationOptions: (navigation) => ({
                title: navigation.navigation.getParam('title', 'Detalhes'),
                headerLeft: () => <MnBackButton />,
            }),
        }
    },
    {
        initialRouteName: 'Home',
        defaultNavigationOptions: defaultNavigationOptions,
        headerTitleAlign: 'center',
    },
);

const AppRoutes = createAppContainer(
    createSwitchNavigator(
        {
            AuthLoading: AuthLoadingNavigator,
            App: AppNavigator,
        },
        {
          initialRouteName: 'AuthLoading',
        }
      )
);

export default AppRoutes;
