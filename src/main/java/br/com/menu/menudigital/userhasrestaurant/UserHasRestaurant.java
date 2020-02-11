package br.com.menu.menudigital.userhasrestaurant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.menu.menudigital.interfaces.SoftDeleteClass;

@Entity
public class UserHasRestaurant implements SoftDeleteClass {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long restaurantId;
	private boolean deleted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@Override
	public boolean isDeleted() {
		return this.deleted;
	}
	
}
