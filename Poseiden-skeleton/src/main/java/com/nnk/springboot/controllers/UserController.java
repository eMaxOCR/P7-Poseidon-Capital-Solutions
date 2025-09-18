package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
	
    @Autowired
    private UserService userService;

    /**
	 * Displays a list of all users.
	 * Only users with the 'ADMIN' role can access this page.
	 *
	 * @param model the Model object to pass data to the view.
	 * @return the view name "user/list" to display the user list.
	 */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    
    /**
	 * Displays the form to add a new user.
	 *
	 * @param user an empty User object for the form.
	 * @return the view name "user/add" which contains the form.
	 */
    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    /**
	 * Validates and saves a new user.
	 *
	 * @param user the User object with form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the user list if validation is successful,
	 * otherwise, it returns to the add form.
	 */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/user/add";
        }
        userService.validate(user);
        return "redirect:/user/list";
    }

    /**
	 * Displays the update form for a specific user.
	 *
	 * @param id the ID of the user to update, taken from the URL.
	 * @param model the Model object to pass the user's data to the view.
	 * @return the view name "user/update" which contains the pre-filled form.
	 */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
	 * Handles the form submission to update a user.
	 *
	 * @param id the ID of the user to update, taken from the URL.
	 * @param user the User object with the new form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the user list if the update is successful,
	 * otherwise returns to the update form.
	 */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        userService.update(user);
        return "redirect:/user/list";
    }
    
    /**
	 * Deletes a user from the database.
	 *
	 * @param id the ID of the user to delete, taken from the URL.
	 * @param model the Model object.
	 * @return a redirect to the user list after deletion.
	 */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteUser(user);
        return "redirect:/user/list";
    }
}
