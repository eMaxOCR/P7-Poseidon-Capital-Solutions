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
	 * Displays the list of all rules.
	 *
	 * @param model the Model object to pass data to the view (HTML).
	 * @return the view name "rulename/list" to display the list.
	 */
    @RequestMapping("/rulename/list")
    public String home(Model model)
    {
    	model.addAttribute("ruleNames", ruleNameService.getAllRuleName());
        model.addAttribute("remoteUser", userService.getCurrentUser());
        
        return "rulename/list";
    }
 
    /**
   	 * Displays the form to add a new rule.
   	 *
   	 * @param bid an empty RuleName object, created by Spring, for the form.
   	 * @return the view name "rulename/add" which contains the form.
   	 */
    @GetMapping("/rulename/add")
    public String addRuleForm(RuleName bid) {
        return "rulename/add";
    }

    /**
	 * Validates and saves a new rule submitted by the form.
	 *
	 * @param ruleName the RuleName object populated with the form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the list of rules if validation is successful,
	 * otherwise, returns to the add form.
	 */
    @PostMapping("/rulename/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "rulename/add";
        }

    	ruleNameService.validate(ruleName); 
        return "redirect:/rulename/list";
    }

    /**
	 * Displays the update form for a specific rule.
	 *
	 * @param id the ID of the rule to modify, taken from the URL.
	 * @param model the Model object to pass the rule's data to the view.
	 * @return the view name "rulename/update" which contains the form.
	 */
    @GetMapping("/rulename/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("ruleName", ruleNameService.getRuleNameById(id));
        return "rulename/update";
    }


    /**
	 * Handles the form submission to update a rule.
	 *
	 * @param id the ID of the rule to update, taken from the URL.
	 * @param ruleName the RuleName object populated with the new form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the list of rules after the update.
	 */
    @PostMapping("/rulename/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	ruleNameService.updateRuleName(ruleName);
        return "redirect:/rulename/list";
    }

    /**
   	 * Deletes a rule from the database.
   	 *
   	 * @param id the ID of the rule to delete, taken from the URL.
   	 * @param model the Model object.
   	 * @return a redirect to the list of rules after the deletion.
   	 */
    @GetMapping("/rulename/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
    	ruleNameService.deleteRuleName(id);
        return "redirect:/rulename/list";
    }
}
