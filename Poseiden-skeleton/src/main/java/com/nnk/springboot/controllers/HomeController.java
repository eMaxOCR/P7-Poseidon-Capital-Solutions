package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
	
	@RequestMapping("/404")
	public String error(Model model)
	{
		return "404";
	}
	
	@RequestMapping("/500")
	public String internalServeurError(Model model)
	{
		return "500";
	}
	
}
