package com.e_commerce_app.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	
	private String token;
	
	private String type = "Bearer";
	
	private String email;

	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtResponse(String token, String type, String email, Collection<? extends GrantedAuthority> collection) {
		super();
		this.token = token;
		this.type = type;
		this.email = email;
		this.authorities = collection;
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

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}