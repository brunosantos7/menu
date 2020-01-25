package br.com.menu.menudigital.category;

import javax.validation.constraints.NotNull;

public class CategoryDTO {
	
	@NotNull
	private String name;
	@NotNull
	private Long menuId;
	@NotNull
	private Long restaurantId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Category toEntity() {
		Category category = new Category();
		category.setName(this.getName());	
		category.setMenuId(this.getMenuId());
		category.setRestaurantId(this.getRestaurantId());
		return category;
	}
	
}
