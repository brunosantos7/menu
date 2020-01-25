import HttpService from './HttpService';

const RestaurantService = {
    getRestaurants: () => {
        return HttpService.doGet('restaurant', {}).then(response => response.data);
    },
};

export default RestaurantService;
