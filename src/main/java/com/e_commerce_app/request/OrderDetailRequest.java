package com.e_commerce_app.request;

import com.e_commerce_app.entity.Order;
import com.e_commerce_app.entity.Product;

import io.swagger.annotations.ApiModelProperty;

public class OrderDetailRequest {

	@ApiModelProperty(example = "null")
	private Long id;
	
	private Order order;
	
	private Product product;
	
	private int price;
	
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}