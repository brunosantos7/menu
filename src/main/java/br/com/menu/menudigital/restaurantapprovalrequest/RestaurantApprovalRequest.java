package br.com.menu.menudigital.restaurantapprovalrequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RestaurantApprovalRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long restaurantId;
	
	public RestaurantApprovalRequest() {
		super();
	}
	public RestaurantApprovalRequest(Long restaurantId) {
		super();
		this.restaurantId = restaurantId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
}
