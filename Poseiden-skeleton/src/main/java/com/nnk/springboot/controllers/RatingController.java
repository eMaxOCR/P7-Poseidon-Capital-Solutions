package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;

@Controller
public class RatingController {

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private UserService userService;

	/**
	 * Displays the list of all ratings.
	 *
	 * @param model the Model object used to pass data to the view (HTML).
	 * @return the name of the view "rating/list" to display the rating list.
	 */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
    	model.addAttribute("ratings", ratingService.getAllRatings());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "rating/list";
    }

    /**
	 * Displays the form to add a new rating.
	 *
	 * @param rating an empty Rating object for the form.
	 * @return the name of the view "rating/add" which contains the form.
	 */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }
    
    /**
	 * Validates and saves a new rating submitted via the form.
	 *
	 * @param rating the Rating object populated with form data. Validation is applied.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the rating list if validation is successful,
	 * otherwise, it returns to the add form.
	 */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
 
		if(result.hasErrors()) {
		return "rating/add";
		}
		
		ratingService.validate(rating);
        return "redirect:/rating/list";
    }
    
    /**
	 * Displays the update form for a specific rating.
	 *
	 * @param id the ID of the rating to be updated, taken from the URL path.
	 * @param model the Model object to pass the rating's data to the view.
	 * @return the name of the view "rating/update" which contains the pre-filled form.
	 */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("rating", ratingService.getRatingById(id));
        return "rating/update";
    }
    /**
	 * Handles the form submission to update a rating.
	 *
	 * @param id the ID of the rating to update, taken from the URL path.
	 * @param rating the Rating object populated with the new form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the rating list after a successful update.
	 */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
    	ratingService.update(rating);
        return "redirect:/rating/list";
    }
    
    /**
	 * Deletes a rating from the database.
	 *
	 * @param id the ID of the rating to delete, taken from the URL path.
	 * @param model the Model object.
	 * @return a redirect to the rating list after the deletion.
	 */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
