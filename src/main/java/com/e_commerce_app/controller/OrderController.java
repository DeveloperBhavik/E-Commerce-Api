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

import com.e_commerce_app.request.OrderRequest;
import com.e_commerce_app.response.OrderResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.OrderService;
import com.e_commerce_app.service.UserService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/order/")
@Api(value = "Order", produces = "application/json")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
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
	 * Save order
	 * @param orderRequest
	 * @return
	 */
	@PostMapping("save")
	@ApiOperation(value = "Save", notes = "Save Product Basic Details")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<OrderResponse> saveOrder(@RequestBody OrderRequest orderRequest) {
		try {
			return orderService.saveOrder(orderRequest);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save Order Details", e);
			return new ResponseData<OrderResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Get order by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get Order Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<OrderResponse> getOrderById(@PathVariable("id") Long id) {
		try {
			return orderService.getOrderById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get Order", e);
			return new ResponseData<OrderResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete order by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete Order By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<OrderResponse> deleteOrderById(@PathVariable("id") Long id) {
		try {
			return orderService.deleteOrderById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete Order", e);
			return new ResponseData<OrderResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}