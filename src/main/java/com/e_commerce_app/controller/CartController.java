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

import com.e_commerce_app.request.CartRequest;
import com.e_commerce_app.response.CartResponse;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.CartService;
import com.e_commerce_app.service.ProductService;
import com.e_commerce_app.service.UserService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/cart/")
@Api(value = "Cart", produces = "application/json")
public class CartController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * User list
	 * @return
	 */
	@GetMapping("userList")
	@ApiOperation(value = "User List", notes = "List Of All User")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object userList() {
		
		try {
			return userService.getListOfAllUser();
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Fetch User List", e);
			return new ResponseData<UserResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Product list
	 * @return
	 */
	@GetMapping("productList")
	@ApiOperation(value = "Product List", notes = "List Of All Product")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object productList() {
		
		try {
			return productService.getListOfAllProduct();
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Fetch Product List", e);
			return new ResponseData<ProductResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Save cart 
	 * @param cartRequest
	 * @return
	 */
	@PostMapping("save")
	@ApiOperation(value = "Save", notes = "Save Cart Basic Details")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object saveCart(@RequestBody CartRequest cartRequest) {
		try {
			return cartService.saveCart(cartRequest);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save Cart Details", e);
			return new ResponseData<CartResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Get cart by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get Cart Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<CartResponse> getCartById(@PathVariable("id") Long id) {
		try {
			return cartService.getCartById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get Cart", e);
			return new ResponseData<CartResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete cart by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete Cart By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<CartResponse> deleteCategoryById(@PathVariable("id") Long id) {
		try {
			return cartService.deleteCartById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete Cart", e);
			return new ResponseData<CartResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}
