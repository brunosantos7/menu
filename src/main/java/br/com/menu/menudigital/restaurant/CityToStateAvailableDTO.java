package br.com.menu.menudigital.restaurant;

public class CityToStateAvailableDTO {
	
	private String city;
	private String state;
	
	public CityToStateAvailableDTO(String city, String state) {
		super();
		this.city = city;
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
