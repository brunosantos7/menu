package br.com.menu.menudigital.user;

import javax.validation.constraints.NotNull;

public class UserDTO  {
	
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private Integer userType;
	@NotNull
	private Long premiumTypeId;
	
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
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPremiumTypeId() {
		return premiumTypeId;
	}

	public void setPremiumTypeId(Long premiumTypeId) {
		this.premiumTypeId = premiumTypeId;
	}

	public User toEntity() {
		User user = new User();
		user.setPassword(this.getPassword());
		user.setUserType(this.getUserType());
		user.setUsername(this.getUsername());
		user.setEmail(this.getEmail());
		user.setPremiumTypeId(this.premiumTypeId);
		
		return user;
	}
	
}