package com.e_commerce_app.service;

import com.e_commerce_app.entity.Category;
import com.e_commerce_app.request.CategoryRequest;
import com.e_commerce_app.response.CategoryResponse;
import com.e_commerce_app.response.ResponseData;

public interface CategoryService {

	ResponseData<CategoryResponse> saveCategory(CategoryRequest categoryRequest);

	Category checkCategoryExistence(String name);

	ResponseData<CategoryResponse> getCategoryById(Long id);

	ResponseData<CategoryResponse> deleteCategoryById(Long id);

	Object getListOfAllCategories();
}