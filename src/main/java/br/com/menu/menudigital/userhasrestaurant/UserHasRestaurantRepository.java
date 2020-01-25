package br.com.menu.menudigital.userhasrestaurant;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserHasRestaurantRepository extends CrudRepository<UserHasRestaurant, Long>{

	List<UserHasRestaurant> findByRestaurantId(Long id);

}
