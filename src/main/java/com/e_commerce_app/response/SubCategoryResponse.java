package com.e_commerce_app.response;

import com.e_commerce_app.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SubCategoryResponse {

	private Long id;
	
	private String name;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Category category;
	
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}