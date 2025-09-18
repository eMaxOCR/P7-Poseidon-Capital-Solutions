package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

@Controller
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private UserService userService;

	/**
	 * Displays the list of all trades.
	 *
	 * @param model the Model object to pass data to the view (HTML).
	 * @return the view name "trade/list" to display the list.
	 */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
    	model.addAttribute("trades", tradeService.getAllTrades());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "trade/list";
    }

    /**
	 * Displays the form to add a new trade.
	 *
	 * @param trade an empty Trade object, created by Spring, for the form.
	 * @return the view name "trade/add" which contains the form.
	 */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
	 * Validates and saves a new trade submitted by the form.
	 *
	 * @param trade the Trade object populated with the form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the list of trades if validation is successful,
	 * otherwise, returns to the add form.
	 */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "trade/add";
        }

    	tradeService.validate(trade); 
        return "redirect:/trade/list";
    }

    /**
	 * Displays the update form for a specific trade.
	 *
	 * @param id the ID of the trade to modify, taken from the URL.
	 * @param model the Model object to pass the trade's data to the view.
	 * @return the view name "trade/update" which contains the pre-filled form.
	 */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("trade", tradeService.getTradeById(id));
        return "trade/update";
    }

    /**
	 * Handles the form submission to update a trade.
	 *
	 * @param id the ID of the trade to update, taken from the URL.
	 * @param trade the Trade object populated with the new form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the list of trades after the update.
	 */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
    	tradeService.update(trade);
        return "redirect:/trade/list";
    }

    /**
	 * Deletes a trade from the database.
	 *
	 * @param id the ID of the trade to delete, taken from the URL.
	 * @param model the Model object.
	 * @return a redirect to the list of trades after the deletion.
	 */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    	tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
