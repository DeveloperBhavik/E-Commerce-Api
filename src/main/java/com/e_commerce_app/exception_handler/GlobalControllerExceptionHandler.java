package com.e_commerce_app.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.e_commerce_app.response.AuthErrorResponse;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {
	
	/**
	 * Handle error response.
	 * @param ex
	 * @param req
	 * @return
	 */
    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AuthErrorResponse unknownException(Exception ex, WebRequest req) {
        return new AuthErrorResponse(ex.getMessage(), 420);
    }
}