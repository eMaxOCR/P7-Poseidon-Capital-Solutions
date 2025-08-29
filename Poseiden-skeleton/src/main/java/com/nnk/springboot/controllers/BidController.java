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
	 * Show bid list
	 * */
    @RequestMapping("/bidlist/list")
    public String home(Model model)
    {
    	model.addAttribute("bids", bidListService.getAllBids());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "bidlist/list";
    }

	/**
	 * Show bid's add form
	 * */
    @GetMapping("/bidlist/add")
    public String addBidForm(Bid bid) {
        return "bidlist/add";
    }

	/**
	 * Add bid
	 * */
    @PostMapping("/bidlist/validate")
    public String validate(@Valid Bid bid, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "bidlist/add"; 
        }

    	bidListService.validate(bid); 
        return "redirect:/bidlist/list";
    }

	/**
	 * Show bid's update form
	 * */
    @GetMapping("/bidlist/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("bid", bidListService.getBidById(id));
        return "bidlist/update";
    }

	/**
	 * Update bid
	 * */
    @PostMapping("/bidlist/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bidList,
                             BindingResult result, Model model) {
    	bidListService.update(bidList);
        return "redirect:/bidlist/list";
    }
    
	/**
	 * Delete bid
	 * */
    @GetMapping("/bidlist/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	bidListService.deleteBid(id);
        return "redirect:/bidlist/list";
    }
}
