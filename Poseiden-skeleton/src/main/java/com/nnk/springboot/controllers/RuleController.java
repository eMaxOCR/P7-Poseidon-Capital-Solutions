package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

@Controller
public class RuleController {
    
	@Autowired
	private RuleNameService ruleNameService;
	
	@Autowired
	private UserService userService;

	/**
	 * Show rule list
	 * */
    @RequestMapping("/rulename/list")
    public String home(Model model)
    {
    	model.addAttribute("ruleNames", ruleNameService.getAllRuleName());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "rulename/list";
    }

    /**
	 * Show rule's add form
	 * */
    @GetMapping("/rulename/add")
    public String addRuleForm(RuleName bid) {
        return "rulename/add";
    }

    /**
	 * Add rule
	 * */
    @PostMapping("/rulename/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "rulename/add";
        }

    	ruleNameService.validate(ruleName); 
        return "redirect:/rulename/list";
    }

    /**
	 * Show rule's update form 
	 * */
    @GetMapping("/rulename/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("ruleName", ruleNameService.getRuleNameById(id));
        return "rulename/update";
    }

    /**
	 * Update rule
	 * */
    @PostMapping("/rulename/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	ruleNameService.updateRuleName(ruleName);
        return "redirect:/rulename/list";
    }

    /**
	 * Delete rule
	 * */
    @GetMapping("/rulename/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
    	ruleNameService.deleteRuleName(id);
        return "redirect:/rulename/list";
    }
}
