package br.com.menu.menudigital.restaurantapprovalrequest;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantApprovalRequestRepository extends CrudRepository<RestaurantApprovalRequest, Long> {

	RestaurantApprovalRequest findByRestaurantId(Long id);

}
