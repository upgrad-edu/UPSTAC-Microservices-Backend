package org.upgrad.upstac.auth.models;

import io.swagger.annotations.ApiModelProperty;

public class LoginRequest {

    @ApiModelProperty(example = "user")
    private String userName;

    @ApiModelProperty(example = "password")
	private String password;

	public LoginRequest() {

	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
