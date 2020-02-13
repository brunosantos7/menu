package br.com.menu.menudigital.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

	List<Restaurant> findAll();

	@Query("SELECT new br.com.menu.menudigital.restaurant.CityToStateAvailableDTO(r.city, r.state) from Restaurant r WHERE r.approved = 1 GROUP BY r.city, r.state")
	List<CityToStateAvailableDTO> findAllCitiesAndSateAvailable();

	List<Restaurant> findByCity(String city);


	List<Restaurant> findByApprovedAndCityIgnoreCaseContaining(boolean approved, String city);

	List<Restaurant> findByApprovedAndNameIgnoreCaseContaining(boolean approved, String name);

	@Query("SELECT r from Restaurant r WHERE r.approved = :approved AND r.city LIKE :cityLike AND r.name LIKE :nameLike")
	List<Restaurant> findByApprovedAndCityAndNameLike(boolean approved, String cityLike, String nameLike);
}
