package com.e_commerce_app.request;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class OrderRequest {

	@ApiModelProperty(example = "null")
	private Long id;
	
	private Long userId;
	
	private int amount;
	
	private Date date;
	
	@ApiModelProperty(example = "null")
	private String status;

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}