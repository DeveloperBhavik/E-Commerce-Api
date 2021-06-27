package com.e_commerce_app.request;

import io.swagger.annotations.ApiModelProperty;

public class CategoryRequest {
	
	@ApiModelProperty(example = "null")
	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}