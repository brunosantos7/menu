import axios from 'axios';
import qs from 'qs';
import { api } from '../constants';

const axiosInstance = axios.create({
    baseURL: api.baseUrl
});

const doGet = (url, params) => {
    return axiosInstance.get(url, { params: params });
}

const doPost = (url, params) => {
    const parsedParams = qs.stringify(params);
    return axiosInstance.post(url, parsedParams);
}

const doDelete = (url, params) => {
    return axiosInstance.delete(url, { params: params });
}

export default {
    doGet: doGet,
    doPost: doPost,
    doDelete: doDelete
}