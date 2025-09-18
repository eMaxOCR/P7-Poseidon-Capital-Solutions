package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import com.nnk.springboot.config.ResourceNotFoundException;

@Controller
public class HomeController
{
	/**
	 * Displays the default home page of the application.
	 *
	 * @param model the Model object used to pass data to the view.
	 * @return the view name "home".
	 */
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	/**
	 * Redirects users with the 'ADMIN' role to the bid list page.
	 *
	 * @param model the Model object.
	 * @return a redirect to the "/bidList/list" URL.
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
	
	/**
	 * Handles ResourceNotFoundException and displays a 404 page.
	 *
	 * @param model the Model object.
	 * @return the view name "404".
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
	@RequestMapping("/404")
	public String error(Model model)
	{
		throw new ResourceNotFoundException("Erreur de test");
	}
	
	/**
	 * Handles RuntimeException to display a 500 page.
	 *
	 * @param model the Model object.
	 * @return the view name "500".
	 */
	@ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@RequestMapping("/500")
	public String internalServeurError(Model model) 
	{
		return"500";
	}
	
}
