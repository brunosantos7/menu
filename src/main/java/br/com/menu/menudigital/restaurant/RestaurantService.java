package br.com.menu.menudigital.restaurant;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import br.com.menu.menudigital.user.User;

public interface RestaurantService {

	boolean softDeleteRestaurant(Restaurant restaurant);
	
	public Restaurant saveRestaurant(RestaurantDTO newRestaurantDTO, MultipartFile file, User user) throws IOException;

}
