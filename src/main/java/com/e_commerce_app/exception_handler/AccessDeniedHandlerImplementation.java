package com.e_commerce_app.exception_handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.e_commerce_app.response.AuthErrorResponse;
import com.e_commerce_app.utils.MessageConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccessDeniedHandlerImplementation implements AccessDeniedHandler {

	/**
	 * Access denied page.
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, 
	AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ObjectMapper mapper = new ObjectMapper();
		AuthErrorResponse authErrorResponse = new AuthErrorResponse(MessageConstants.FORBIDDEN_USER,420);
		response.setContentType("application/json");
		response.getWriter().write( mapper.writeValueAsString(authErrorResponse));
	}
}