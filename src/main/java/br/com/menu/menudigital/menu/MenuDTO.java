package br.com.menu.menudigital.menu;

public class MenuDTO {
	
	private String title;
	private Long restaurantId;

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
	
	public Menu toMenuEntity() {
		Menu menu = new Menu();
		menu.setRestaurantId(this.getRestaurantId());
		menu.setTitle(this.getTitle());
		return menu;
	}
}
