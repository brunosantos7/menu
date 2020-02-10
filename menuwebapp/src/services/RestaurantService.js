import HttpService from './HttpService';

export default {
  getMenusByRestaurantId: restaurantId =>
    HttpService.get(`/restaurant/${restaurantId}/menus`)
};
