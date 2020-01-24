package br.com.menu.menudigital.user;

public class UserDTO  {
	
	private String username;
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

	public User toEntity() {
		User user = new User();
		user.setPassword(this.getPassword());
		user.setUserType(this.getUserType());
		user.setRestaurantId(this.getRestaurantId());
		user.setUsername(this.getUsername());
		
		return user;
	}
	
}