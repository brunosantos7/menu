package br.com.menu.menudigital.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.menu.menudigital.interfaces.SoftDeleteClass;

@Entity
public class Product implements SoftDeleteClass {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Double price;

	@JsonIgnore
	private boolean deleted;
	
	@Column(name="category_id")
	private Long categoryId;

	private Long restaurantId;

	@JsonIgnore
	private String imagePath;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public boolean isDeleted() {
		return this.deleted;
	}
	
	public String getImageUri() {
		if(this.imagePath != null) {
			return ServletUriComponentsBuilder.fromCurrentContextPath()
				    .path(String.format("images/product/%s", this.id))
				    .toUriString();
		}	
		
		return ServletUriComponentsBuilder.fromCurrentContextPath()
			    .path("images/noimage")
			    .toUriString();
		
	}
	
}
