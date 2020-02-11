package br.com.menu.menudigital.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

	List<Restaurant> findAll();

	@Query("SELECT new br.com.menu.menudigital.restaurant.CityToStateAvailableDTO(r.city, r.state) from Restaurant r GROUP BY r.city, r.state")
	List<CityToStateAvailableDTO> findAllCitiesAndSateAvailable();

	List<Restaurant> findByCity(String city);

	List<Restaurant> findByCityOrName(String city, String name);
}
