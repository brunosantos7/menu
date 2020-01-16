package br.com.menu.menudigital.product;

public class ProductDTO {

	private Long id;
	private String name;
	private String description;
	private Long categoryId;
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
	
	public Product toEntity() {
		Product product = new Product();
		product.setCategoryId(this.getCategoryId());
		product.setDescription(this.getDescription());
		product.setName(this.getName());
		product.setId(this.getId());
		return product;
	}
}
