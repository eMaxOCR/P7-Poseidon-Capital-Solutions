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
        return "redirect:/404";
    }
	
	/**
	 * Error 500 : Internal server error
	 * */
	@ExceptionHandler(Exception.class)
    public String exception(Exception ex) {
        return "redirect:/500";
    }
}