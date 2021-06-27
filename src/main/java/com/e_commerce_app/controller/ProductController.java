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

import com.e_commerce_app.entity.Product;
import com.e_commerce_app.request.ProductRequest;
import com.e_commerce_app.response.CategoryResponse;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.SubCategoryResponse;
import com.e_commerce_app.service.CategoryService;
import com.e_commerce_app.service.ProductService;
import com.e_commerce_app.service.SubCategoryService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/product/")
@Api(value = "Product", produces = "application/json")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	@Autowired
	private ProductService productService;
	
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
	 * Sub-Category list
	 * @return
	 */
	@GetMapping("subCategoryList")
	@ApiOperation(value = "Sub-Category List", notes = "List Of All Sub-Categories")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object subCategoryList() {
		
		try {
			return subCategoryService.getListOfAllSubCategories();
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Fetch Sub-Category List", e);
			return new ResponseData<SubCategoryResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Save product
	 * @param productRequest
	 * @return
	 */
	@PostMapping("save")
	@ApiOperation(value = "Save", notes = "Save Product Basic Details")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<ProductResponse> saveProduct(@RequestBody ProductRequest productRequest) {
		try {
			ResponseData<ProductResponse> response = (productRequest.getId() == null ? checkProductNameExistence(productRequest):productService.saveProduct(productRequest));
			return response;
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save Product Details", e);
			return new ResponseData<ProductResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Check product name existence
	 * @param subCategoryRequest
	 * @return
	 */
	private ResponseData<ProductResponse> checkProductNameExistence(ProductRequest productRequest) {
		
		Product product = productService.checkProductExistence(productRequest.getName());
		ResponseData<ProductResponse> response = (product == null ? productService.saveProduct(productRequest) : new ResponseData<ProductResponse>(MessageConstants.PRODUCT_EXIST, null, 420));
		return response;
	}
	
	/**
	 * Get product by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get Product Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<ProductResponse> getProductById(@PathVariable("id") Long id) {
		try {
			return productService.getProductById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get Product", e);
			return new ResponseData<ProductResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete product by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete Product By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<ProductResponse> deleteProductById(@PathVariable("id") Long id) {
		try {
			return productService.deleteProductById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete Product", e);
			return new ResponseData<ProductResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}