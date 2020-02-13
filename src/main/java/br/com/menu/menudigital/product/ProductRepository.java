package br.com.menu.menudigital.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findByCategoryId(Long id);

	List<Product> findByRestaurantId(Long id);

	@Query("SELECT COUNT(p) FROM Product p WHERE p.restaurantId IN (:restaurantIds)")
	int countByRestaurantIds(List<Long> restaurantIds);

}
