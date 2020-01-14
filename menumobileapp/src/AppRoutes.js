import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import Home from './pages/Home';
import CategoryList from './pages/CategoryList';
import ProductList from './pages/ProductList';
import ProductDetail from './pages/ProductDetail';

const AppNavigator = createStackNavigator(
    {
        Home: {
            screen: Home,
        },
        CategoryList: {
            screen: CategoryList,
        },
        ProductList: {
            screen: ProductList,
        },
        ProductDetail: {
            screen: ProductDetail,
        }
    },
    {
        initialRouteName: 'Home',
    },
);

const AppRoutes = createAppContainer(AppNavigator);

export default AppRoutes;
