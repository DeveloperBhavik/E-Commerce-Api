package com.e_commerce_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce_app.entity.Category;
import com.e_commerce_app.request.CategoryRequest;
import com.e_commerce_app.response.CategoryResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.CategoryService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/category/")
@Api(value = "Category", produces = "application/json")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * Save category
	 * @param categoryRequest
	 * @return
	 */
	@PostMapping("save")
	@ApiOperation(value = "Save", notes = "Save Category Basic Details")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<CategoryResponse> saveCategory(@RequestBody CategoryRequest categoryRequest) {
		try {
			ResponseData<CategoryResponse> response = (categoryRequest.getId() == null ? checkCategoryNameExistence(categoryRequest):categoryService.saveCategory(categoryRequest));
			return response;
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save User Details", e);
			return new ResponseData<CategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Check category name existence
	 * @param categoryRequest
	 * @return
	 */
	private ResponseData<CategoryResponse> checkCategoryNameExistence(CategoryRequest categoryRequest) {
		
		Category category = categoryService.checkCategoryExistence(categoryRequest.getName());
		ResponseData<CategoryResponse> response = (category == null ? categoryService.saveCategory(categoryRequest) : new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_EXIST, null, 420));
		return response;
	}
	
	/**
	 * Get category by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get Category Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
		try {
			return categoryService.getCategoryById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get Category", e);
			return new ResponseData<CategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete category by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete Category By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<CategoryResponse> deleteCategoryById(@PathVariable("id") Long id) {
		try {
			return categoryService.deleteCategoryById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete Category", e);
			return new ResponseData<CategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}