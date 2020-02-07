package br.com.menu.menudigital.restaurant;

import javax.validation.constraints.NotNull;

public class RestaurantDTO {

	@NotNull
	private String name;
	@NotNull
	private String street;
	@NotNull
	private String neighborhood;
	@NotNull
	private String state;
	@NotNull
	private String cep;
	@NotNull
	private String city;
	@NotNull
	private Integer number;
	@NotNull
	private String phone;
	private String email;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Restaurant toRestaurantEntity() {
		Restaurant rest = new Restaurant();
		
		rest.setName(this.getName());
		rest.setStreet(this.getStreet());
		rest.setNeighborhood(this.getNeighborhood());
		rest.setState(this.getState());
		rest.setCep(this.getCep());
		rest.setCity(this.getCity());
		rest.setNumber(this.getNumber());
		rest.setPhone(this.getPhone());
		rest.setEmail(this.getEmail());
		return rest;
	}
	
}
