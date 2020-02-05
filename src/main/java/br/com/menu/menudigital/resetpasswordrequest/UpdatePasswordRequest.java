package br.com.menu.menudigital.resetpasswordrequest;

import javax.validation.constraints.NotNull;

public class UpdatePasswordRequest {

	@NotNull
	private String resetPasswordRequestId;
	@NotNull
	private String newPassword;
	
	public String getResetPasswordRequestId() {
		return resetPasswordRequestId;
	}
	public void setResetPasswordRequestId(String resetPasswordRequestId) {
		this.resetPasswordRequestId = resetPasswordRequestId;
	}
	public String getNewPassword() {
		return this.newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
