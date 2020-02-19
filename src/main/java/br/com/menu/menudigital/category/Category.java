package br.com.menu.menudigital.category;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.menu.menudigital.interfaces.SoftDeleteClass;
import br.com.menu.menudigital.product.Product;

@Entity
public class Category implements SoftDeleteClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long menuId;

	@JsonIgnore
	private boolean deleted;
	@JsonIgnore
	private Long restaurantId;
	@JsonIgnore
	private String imagePath;

	@OneToMany
    @JoinColumn(name = "category_id")
	private List<Product> products;

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getImageUri() {
		if (this.imagePath != null) {
			return ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(String.format("images/category/%s", this.id)).toUriString();
		}

		return ServletUriComponentsBuilder.fromCurrentContextPath()
			    .path("images/noimage")
			    .toUriString();
		
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
	}

}
