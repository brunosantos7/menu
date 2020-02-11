package br.com.menu.menudigital.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

	List<Restaurant> findAll();

	@Query("SELECT new br.com.menu.menudigital.restaurant.CityToStateAvailableDTO(r.city, r.state) from Restaurant r GROUP BY r.city, r.state")
	List<CityToStateAvailableDTO> findAllCitiesAndSateAvailable();

	List<Restaurant> findByCity(String city);


	@Query("SELECT r from Restaurant r WHERE r.city LIKE :cityLike AND r.name LIKE :nameLike")
	List<Restaurant> findByCityAndNameLike(String cityLike, String nameLike);

	List<Restaurant> findByCityIgnoreCaseContaining(String city);

	List<Restaurant> findByNameIgnoreCaseContaining(String name);
}
