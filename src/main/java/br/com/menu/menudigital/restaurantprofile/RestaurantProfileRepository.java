package br.com.menu.menudigital.restaurantprofile;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantProfileRepository extends CrudRepository<RestaurantProfile, Long> {

	List<RestaurantProfile> findByRestaurantId(Long id);

}
