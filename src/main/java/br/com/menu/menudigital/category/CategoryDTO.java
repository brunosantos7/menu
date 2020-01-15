package br.com.menu.menudigital.category;

public class CategoryDTO {
	
	private String name;
	private String imagePath;
	private Long menuId;
	
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
	
	public Category toEntity() {
		Category category = new Category();
		category.setName(this.getName());	
		category.setImagePath(this.getImagePath());
		category.setMenuId(this.getMenuId());
		return category;
	}
	
}
