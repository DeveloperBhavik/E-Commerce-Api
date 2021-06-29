package com.e_commerce_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce_app.entity.User;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.UserService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
@Api(value = "User", produces = "application/json")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * Check user login
	 * @return
	 */
	@ApiOperation(value = "Check is user login", notes = "Check is user login")
	@RequestMapping(value = "checkLogin", method = RequestMethod.GET)
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, defaultValue = "Bearer ", dataType = "string", paramType = "header")})
	public Object checkLogin() {
		
		try {
			return userService.checkLogin();
		} catch (Exception e) {
			LOGGER.error("Error while checking user is login or not", e);
			return new ResponseData<User>(MessageConstants.LOGIN_FAIL, null, 420);
		}
	}
	
	
	
	/**
	 * Get user by id
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "Get", notes = "Get User Details By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<UserResponse> getUserById(@PathVariable("id") Long id) {
		try {
			return userService.getUserById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Get User", e);
			return new ResponseData<UserResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Delete user by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "Delete", notes = "Delete User By Id")
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer " , required = true, dataType = "string", paramType = "header")})
	public ResponseData<UserResponse> deleteUserById(@PathVariable("id") Long id) {
		try {
			return userService.deleteUserById(id);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Delete User", e);
			return new ResponseData<UserResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
}