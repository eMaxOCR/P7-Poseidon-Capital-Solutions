package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BidController {

	@Autowired
	private BidListService bidListService;
	
	@Autowired
	private UserService userService;

	/**
	 * Displays the list of all bids.
	 *
	 * @param model the Model object used to pass data to the view (HTML).
	 * @return the name of the view "bidlist/list" to display the bid list.
	 */
    @RequestMapping("/bidlist/list")
    public String home(Model model)
    {
    	
    	model.addAttribute("bids", bidListService.getAllBids());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "bidlist/list";
    }

    /**
	 * Displays the form to add a new bid.
	 *
	 * @param bid an empty Bid object, created by Spring, to be used by the form.
	 * @return the name of the view "bidlist/add" which contains the form.
	 */
    @GetMapping("/bidlist/add")
    public String addBidForm(Bid bid) {
        return "bidlist/add";
    }

    /**
	 * Validates and saves a new bid submitted via the form.
	 *
	 * @param bid the Bid object populated with form data. Validation is applied.
	 * @param result the object that holds the results of the validation.
	 * @param model the Model object to pass data in case of errors.
	 * @return a redirect to the list of bids if validation is successful,
	 * otherwise, it returns to the add form.
	 */
    @PostMapping("/bidlist/validate")
    public String validate(@Valid Bid bid, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "bidlist/add"; 
        }

    	bidListService.validate(bid); 
        return "redirect:/bidlist/list";
    }

    /**
	 * Displays the update form for a specific bid.
	 *
	 * @param id the ID of the bid to be updated, taken from the URL path.
	 * @param model the Model object to pass the bid's data to the view.
	 * @return the name of the view "bidlist/update" which contains the pre-filled form.
	 */
    @GetMapping("/bidlist/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("bid", bidListService.getBidById(id));
        return "bidlist/update";
    }

    /**
	 * Handles the form submission to update a bid.
	 *
	 * @param id the ID of the bid to update, taken from the URL path.
	 * @param bidList the Bid object populated with the new form data.
	 * @param result the object that holds the results of the validation.
	 * @param model the Model object.
	 * @return a redirect to the list of bids after a successful update.
	 */
    @PostMapping("/bidlist/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bidList,
                             BindingResult result, Model model) {
    	bidListService.update(bidList);
        return "redirect:/bidlist/list";
    }
    
    /**
	 * Deletes a bid from the database.
	 *
	 * @param id the ID of the bid to delete, taken from the URL path.
	 * @param model the Model object.
	 * @return a redirect to the list of bids after the deletion.
	 */
    @GetMapping("/bidlist/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	bidListService.deleteBid(id);
        return "redirect:/bidlist/list";
    }
     
}
