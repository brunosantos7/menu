package br.com.menu.menudigital.menu;

public class MenuDTO {
	
	private String title;
	private Long restaurantProfileId;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getRestaurantProfileId() {
		return restaurantProfileId;
	}
	public void setRestaurantProfileId(Long restaurantProfileId) {
		this.restaurantProfileId = restaurantProfileId;
	}
	public Menu toMenuEntity() {
		Menu menu = new Menu();
		menu.setRestaurantProfileId(this.getRestaurantProfileId());
		menu.setTitle(this.getTitle());
		return menu;
	}
}
