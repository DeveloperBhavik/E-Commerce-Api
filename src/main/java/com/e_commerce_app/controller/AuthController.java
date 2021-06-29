package com.e_commerce_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce_app.config.service.TokenProvider;
import com.e_commerce_app.entity.User;
import com.e_commerce_app.request.LoginRequest;
import com.e_commerce_app.request.UserRequest;
import com.e_commerce_app.response.JwtResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.UserService;
import com.e_commerce_app.utils.MessageConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/auth/")
@Api(value = "Auth", produces = "application/json")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private UserService userService;

	/**
	 * Login api
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("login")
	public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = tokenProvider.createAccessToken(authentication);
		return new JwtResponse(accessToken, "Bearer", authentication.getName(), authentication.getAuthorities().toString());
	}
	
	/**
	 * Save user
	 * @param userRequest
	 * @return
	 */
	@PostMapping("register")
	@ApiOperation(value = "Save", notes = "Save User Basic Details")
	public ResponseData<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
		try {
			ResponseData<UserResponse> response = (userRequest.getId() == null ? checkEmailAddressExistence(userRequest):userService.saveUser(userRequest));
			return response;
		} catch (Exception e) {
			LOGGER.error("Exception Occurred When Try To Save User Details", e);
			return new ResponseData<UserResponse>(MessageConstants.PLEASE_TRY_AGAIN, null, 420);
		}
	}
	
	/**
	 * Check email existence
	 * @param userRequest
	 * @return
	 */
	private ResponseData<UserResponse> checkEmailAddressExistence(UserRequest userRequest) {
		
		User user = userService.checkEmailAddressExistance(userRequest.getEmail());
		ResponseData<UserResponse> response = (user == null ? userService.saveUser(userRequest) : new ResponseData<UserResponse>(MessageConstants.USER_EXIST, null, 420));
		return response;
	}
}