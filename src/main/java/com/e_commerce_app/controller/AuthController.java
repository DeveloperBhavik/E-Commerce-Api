package com.e_commerce_app.controller;

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
import com.e_commerce_app.request.LoginRequest;
import com.e_commerce_app.response.JwtResponse;

import io.swagger.annotations.Api;

@CrossOrigin
@RestController
@RequestMapping("/api/auth/")
@Api(value = "Auth", produces = "application/json")
public class AuthController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

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
		return new JwtResponse(accessToken, "Bearer", authentication.getName(), authentication.getAuthorities());
	}
}