package br.com.menu.menudigital.menu;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Long> {

	List<Menu> findByRestaurantId(Long restaurantId);

	@Query("SELECT COUNT(m) FROM Menu m WHERE m.restaurantId IN (:restaurantIds)")
	int countByRestaurantIds(List<Long> restaurantIds);

}
