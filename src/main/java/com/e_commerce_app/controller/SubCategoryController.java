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

import com.e_commerce_app.entity.SubCategory;
import com.e_commerce_app.request.SubCategoryRequest;
import com.e_commerce_app.response.CategoryResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.SubCategoryResponse;
import com.e_commerce_app.service.CategoryService;
import com.e_commerce_app.service.SubCategoryService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/sub_category/")
@Api(value = "Sub Category", produces = "application/json")
public class SubCategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryController.class);
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * Category list
	 * @return
	 */
	@GetMapping("categoryList")
	@ApiOperation(value = "Category List", notes = "List Of All Categories")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object categoryList() {
		
		try {
			return categoryService.getListOfAllCategories();
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Fetch Category List", e);
			return new ResponseData<CategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Save sub_category
	 * @param subCategoryRequest
	 * @return
	 */
	@PostMapping("save")
	@ApiOperation(value = "Save", notes = "Save Sub-Category Basic Details")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<SubCategoryResponse> saveCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
		try {
			ResponseData<SubCategoryResponse> response = (subCategoryRequest.getId() == null ? checkSubCategoryNameExistence(subCategoryRequest):subCategoryService.saveSubCategory(subCategoryRequest));
			return response;
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save Sub-Category Details", e);
			return new ResponseData<SubCategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Check sub-category name existence
	 * @param subCategoryRequest
	 * @return
	 */
	private ResponseData<SubCategoryResponse> checkSubCategoryNameExistence(SubCategoryRequest subCategoryRequest) {
		
		SubCategory subCategory = subCategoryService.checkSubCategoryExistence(subCategoryRequest.getName());
		ResponseData<SubCategoryResponse> response = (subCategory == null ? subCategoryService.saveSubCategory(subCategoryRequest) : new ResponseData<SubCategoryResponse>(MessageConstants.SUB_CATEGORY_EXIST, null, 420));
		return response;
	}
	
	/**
	 * Get sub-category by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get Sub-Category Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object getSubCategoryById(@PathVariable("id") Long id) {
		try {
			return subCategoryService.getSubCategoryById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get Sub Category", e);
			return new ResponseData<SubCategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete sub-category by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete Sub-Category By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<SubCategoryResponse> deleteSubCategoryById(@PathVariable("id") Long id) {
		try {
			return subCategoryService.deleteSubCategoryById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete Sub Category", e);
			return new ResponseData<SubCategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}