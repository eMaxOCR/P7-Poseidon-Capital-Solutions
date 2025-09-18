package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    /**
	 * Displays the login page.
	 *
	 * @return a ModelAndView for the "login" view.
	 */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
	 * Displays the signup form.
	 *
	 * @return a ModelAndView for the "signup" view, with a new User object.
	 */
    @GetMapping("/signup")
    public ModelAndView signup() {
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");
        mav.addObject("user", new User()); 
        return mav;
    }
    
    /**
	 * Validates and saves a new user.
	 *
	 * @param user the User object with form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the bid list if the signup is successful; otherwise, it returns to the signup form.
	 */
    @PostMapping("/signup")
    public String signup(@Valid User user,BindingResult result, Model model ) {
    	if (result.hasErrors()) {
    		 result.getAllErrors().forEach(System.out::println);
            return "signup"; 
        }

    	userService.validate(user); 
        return "redirect:/bidlist/list";
    }
    
    /**
	 * Handles user logout.
	 *
	 * @return a ModelAndView that redirects to the login page.
	 */
    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
	 * Displays a list of all users for a secure page.
	 *
	 * @return a ModelAndView for the "user/list" view, with the list of all users.
	 */
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
	 * Handles displaying a 403 authorization error page.
	 *
	 * @return a ModelAndView for the "403" view with an error message.
	 */
    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
