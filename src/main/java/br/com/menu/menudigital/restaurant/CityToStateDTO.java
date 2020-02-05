package br.com.menu.menudigital.restaurant;

public class CityToStateDTO {
	
	private String city;
	private String state;
	
	public CityToStateDTO(String city, String state) {
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
