import HttpService from './HttpService';

const CategoryService = {
    getProducts: (categoryId) => {
        return HttpService.doGet('category/' + categoryId + '/products', {}).then(response => response.data);
    },
};

export default CategoryService;
