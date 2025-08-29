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

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
    	model.addAttribute("ratings", ratingService.getAllRatings());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	//TODO : Add constraint : At least one of the 3 label has to be not null. + Order = unique ?
    	try {
    		if(!result.hasErrors()) {
    			ratingService.validate(rating);
    		return "redirect:/rating/list";
    		}
    	}catch (Exception e) {
    		System.out.println(e);
    	}
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("rating", ratingService.getRatingById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
    	ratingService.update(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
