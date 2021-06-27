package com.e_commerce_app.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	
	private String token;
	
	private String type = "Bearer";
	
	private String firstname;

	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtResponse(String token, String type, String firstname, Collection<? extends GrantedAuthority> collection) {
		super();
		this.token = token;
		this.type = type;
		this.firstname = firstname;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}