package com.e_commerce_app.response;

public class ProductResponse {

	private Long id;
	
	private String name;
	
	private int price;
	
	private int quantity;
	
	private String categoryName;
	
	private String subCategoryName;
	
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
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