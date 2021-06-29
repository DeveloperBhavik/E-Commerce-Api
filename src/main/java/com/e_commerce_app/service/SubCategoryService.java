package com.e_commerce_app.service;

import com.e_commerce_app.entity.SubCategory;
import com.e_commerce_app.request.SubCategoryRequest;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.SubCategoryResponse;

public interface SubCategoryService {

	Object saveSubCategory(SubCategoryRequest subCategoryRequest);

	SubCategory checkSubCategoryExistence(String name);

	Object getSubCategoryById(Long id);

	ResponseData<SubCategoryResponse> deleteSubCategoryById(Long id);

	Object getListOfAllSubCategories();
}