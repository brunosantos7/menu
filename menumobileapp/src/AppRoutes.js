import React from 'react';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import Home from './pages/Home';
import CategoryList from './pages/CategoryList';
import ProductList from './pages/ProductList';
import ProductDetail from './pages/ProductDetail';
import MnBackButton from './components/MnBackButton';
import { colors } from './constants';

const headerStyle = {
    backgroundColor: colors.orange,
};

const headerTitleStyle = {
    fontSize: 21,
    fontFamily: 'Muli',
    color: colors.white,
};

const defaultNavigationOptions = {
    headerStyle: headerStyle,
    headerTitleStyle: headerTitleStyle,
};

const AppNavigator = createStackNavigator(
    {
        Home: {
            screen: Home,
            navigationOptions: (navigation) => ({
                title: navigation.navigation.getParam('title', 'Menu'),
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

const AppRoutes = createAppContainer(AppNavigator);

export default AppRoutes;
