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

import com.e_commerce_app.request.OrderDetailRequest;
import com.e_commerce_app.response.OrderDetailResponse;
import com.e_commerce_app.response.OrderResponse;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.OrderDetailService;
import com.e_commerce_app.service.OrderService;
import com.e_commerce_app.service.ProductService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/order_detail/")
@Api(value = "Order Detail", produces = "application/json")
public class OrderDetailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
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
	 * Order list
	 * @return
	 */
	@GetMapping("orderList")
	@ApiOperation(value = "Order List", notes = "List Of All Order")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object orderList() {
		
		try {
			return orderService.getListOfAllOrder();
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Fetch Order List", e);
			return new ResponseData<OrderResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Save order detail
	 * @param orderDetailRequest
	 * @return
	 */
	@PostMapping("save")
	@ApiOperation(value = "Save", notes = "Save Order Detail Basic Details")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public Object saveOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest) {
		try {
			return orderDetailService.saveOrderDetail(orderDetailRequest);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save Order Details", e);
			return new ResponseData<OrderDetailResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Get order detail by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get Order Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<OrderDetailResponse> getOrderDetailById(@PathVariable("id") Long id) {
		try {
			return orderDetailService.getOrderDetailById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get Order Detail", e);
			return new ResponseData<OrderDetailResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete order by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete Order Detail By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<OrderDetailResponse> deleteOrderDetailById(@PathVariable("id") Long id) {
		try {
			return orderDetailService.deleteOrderDetailById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete Order Detail", e);
			return new ResponseData<OrderDetailResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}
