package com.example.bildergaleriebackend.model;

public class loginDTO {

	private String token;
	private String userName;
	private String email;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
