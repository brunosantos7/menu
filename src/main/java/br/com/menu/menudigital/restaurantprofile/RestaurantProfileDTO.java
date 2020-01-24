package br.com.menu.menudigital.restaurantprofile;

public class RestaurantProfileDTO {
	private Long id;
	private String street;
	private String neighborhood;
	private String state;
	private String cep;
	private String city;
	private String number;
	private String phone;
	private String email;
	private Long restaurantId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
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
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public RestaurantProfile toEntity() {
		RestaurantProfile restaurantProfile = new RestaurantProfile();
		restaurantProfile.setCep(this.getCep());
		restaurantProfile.setCity(this.getCity());
		restaurantProfile.setEmail(this.getEmail());
		restaurantProfile.setNeighborhood(this.getNeighborhood());
		restaurantProfile.setNumber(this.getNumber());
		restaurantProfile.setPhone(this.getPhone());
		restaurantProfile.setRestaurantId(this.getRestaurantId());
		restaurantProfile.setState(this.getState());
		restaurantProfile.setStreet(this.getStreet());
		
		return restaurantProfile;
	}
}
