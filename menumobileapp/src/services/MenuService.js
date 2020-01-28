import HttpService from './HttpService';

const MenuService = {
    getCategories: (restaurantId) => {
        return HttpService.doGet('menu/' + restaurantId + '/categories', {}).then(response => response.data);
    }
};

export default MenuService;
