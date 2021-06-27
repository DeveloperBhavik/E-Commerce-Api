package com.e_commerce_app.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.e_commerce_app.entity.User;
import com.e_commerce_app.request.UserRequest;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;

public interface UserService {
	
	Object checkLogin();
	
	ResponseData<UserResponse> saveUser(UserRequest userRequest);

	User findUserByEmail(String email);

	User findLoginUser();

	Collection<? extends GrantedAuthority> getUserRoleList(String username);

	ResponseData<UserResponse> getUserById(Long id);

	ResponseData<UserResponse> deleteUserById(Long id);

	User checkEmailAddressExistance(String email);

	Object getListOfAllUser();
}