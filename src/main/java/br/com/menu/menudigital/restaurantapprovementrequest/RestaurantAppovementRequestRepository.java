package br.com.menu.menudigital.restaurantapprovementrequest;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantAppovementRequestRepository extends CrudRepository<RestaurantAppovementRequest, Long> {

	RestaurantAppovementRequest findByRestaurantId(Long id);

}
