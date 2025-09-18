package com.nnk.springboot.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Error 404 : Resource not found
	 * */
	@ExceptionHandler(ResourceNotFoundException.class)
    public String notFoundException(ResourceNotFoundException ex) {
        return "404";
    }
	
	/**
	 * Error 500 : Internal server error
	 * */
	@ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex) {
        return "500";
    }
}