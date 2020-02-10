import HttpService from './HttpService';

export default {
  getProductsByCategoryId: categoryId =>
    HttpService.get(`/category/${categoryId}/products`)
};
