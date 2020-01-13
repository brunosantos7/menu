package br.com.menu.menudigital.restaurant;

public class RestaurantDTO {

	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Restaurant toRestaurantEntity() {
		Restaurant rest = new Restaurant();
		rest.setName(this.getName());
		
		return rest;
	}
	
}
