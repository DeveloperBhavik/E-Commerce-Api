package com.e_commerce_app.response;

import com.e_commerce_app.entity.Product;
import com.e_commerce_app.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CartResponse {

	private Long id;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Product product;
	
	private int quantity;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}