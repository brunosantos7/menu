import HttpService from './HttpService';

export default {
  getCategoriesByMenuId: menuId => HttpService.get(`/menu/${menuId}/categories`)
};
