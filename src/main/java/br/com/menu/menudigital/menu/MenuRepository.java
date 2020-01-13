package br.com.menu.menudigital.menu;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Long>{

	List<Menu> findByRestaurantId(Long restaurantId);

}
