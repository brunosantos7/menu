import HttpService from './HttpService';

const RestaurantService = {
    getRestaurants: () => {
        return HttpService.doGet('restaurant', {}).then(response => response.data);
    },
    getMenus: (restaurantId) => {
        return HttpService.doGet('restaurant/' + restaurantId + '/menus', {}).then(response => response.data);
    },
};

export default RestaurantService;
