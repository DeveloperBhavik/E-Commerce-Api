package com.e_commerce_app.response;

public class JwtResponse {
	
	private String token;
	
	private String type = "Bearer";
	
	private String email;

	private String role;
	
	public JwtResponse(String token, String type, String email, String role) {
		super();
		this.token = token;
		this.type = type;
		this.email = email;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}