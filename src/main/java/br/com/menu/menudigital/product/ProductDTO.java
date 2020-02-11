package br.com.menu.menudigital.product;

import javax.validation.constraints.NotNull;

public class ProductDTO {

	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Long categoryId;
	@NotNull
	private Long restaurantId;
	@NotNull
	private Double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Product toEntity() {
		Product product = new Product();
		
		product.setCategoryId(this.getCategoryId());
		product.setDescription(this.getDescription());
		product.setName(this.getName());
		product.setRestaurantId(this.getRestaurantId());
		product.setPrice(this.getPrice());
		return product;
	}
}
