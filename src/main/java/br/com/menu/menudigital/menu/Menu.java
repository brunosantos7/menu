package br.com.menu.menudigital.menu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.menu.menudigital.interfaces.SoftDeleteClass;

@Entity
@Table(name="menu")
public class Menu implements SoftDeleteClass {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Long restaurantId;
	
	@JsonIgnore
	private boolean deleted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
