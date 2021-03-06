package com.e_commerce_app.request;

import io.swagger.annotations.ApiModelProperty;

public class CartRequest {

	@ApiModelProperty(example = "null")
	private Long id;
	
	private Long userId;
	
	private Long productId;
	
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}