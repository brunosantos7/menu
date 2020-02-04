package br.com.menu.menudigital.user;

import javax.validation.constraints.NotNull;

public class UserDTO  {
	
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String password;
	private Integer userType;
	private Long restaurantId;
	
	public UserDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User toEntity() {
		User user = new User();
		user.setPassword(this.getPassword());
		user.setUserType(this.getUserType());
		user.setUsername(this.getUsername());
		user.setEmail(this.getEmail());
		
		return user;
	}
	
}