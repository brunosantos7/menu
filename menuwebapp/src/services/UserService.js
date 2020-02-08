import HttpService from "./HttpService";

export default {
  getRestaurantsByUserId: userId =>
    HttpService.get(`/user/${userId}/restaurants`)
};
