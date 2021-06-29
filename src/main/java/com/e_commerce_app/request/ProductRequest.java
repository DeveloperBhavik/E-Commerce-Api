package com.e_commerce_app.request;

import com.e_commerce_app.entity.Category;
import com.e_commerce_app.entity.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

public class ProductRequest {

	@ApiModelProperty(example = "null")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int quantity;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Category category;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private SubCategory subCategory;
	
	private String description;
	
	private String warrantyInfo;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWarrantyInfo() {
		return warrantyInfo;
	}

	public void setWarrantyInfo(String warrantyInfo) {
		this.warrantyInfo = warrantyInfo;
	}
}