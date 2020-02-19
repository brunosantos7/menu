package br.com.menu.menudigital.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	List<Category> findByMenuId(Long id);

	List<Category> findByRestaurantId(Long id);
	
    @Query("SELECT c FROM Category c FETCH ALL PROPERTIES WHERE c.restaurantId = :restaurantId")
	List<Category> findByRestaurantIdWithProducts(Long restaurantId);


	@Query("SELECT COUNT(c) FROM Category c WHERE c.restaurantId IN (:restaurantIds)")
	int countByRestaurantIds(List<Long> restaurantIds);

}
