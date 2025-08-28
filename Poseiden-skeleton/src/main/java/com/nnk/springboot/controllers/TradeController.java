package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.CurveService;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private UserService userService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
    	model.addAttribute("trades", tradeService.getAllTrades());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "trade/add";
        }

    	tradeService.validate(trade); 
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("trade", tradeService.getTradeById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
    	tradeService.update(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    	tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
