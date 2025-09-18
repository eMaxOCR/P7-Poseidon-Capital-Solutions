package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
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
public class CurveController {

	@Autowired
	private CurveService curveService;
	
	@Autowired
	private UserService userService;
 
	/**
	 * Displays a list of all curve points.
	 *
	 * @param model the Model object used to pass data to the view (HTML).
	 * @return the name of the view "curvepoint/list" to display the list.
	 */
    @RequestMapping("/curvepoint/list")
    public String curvePointList(Model model)
    {
        model.addAttribute("curvePoints", curveService.getAllCurvePoint());
        model.addAttribute("remoteUser", userService.getCurrentUser());

        return "curvepoint/list";
    }

    /**
	 * Displays the form to add a new curve point.
	 *
	 * @param curvePoint an empty CurvePoint object for the form.
	 * @return the name of the view "curvepoint/add" which contains the form.
	 */
    @GetMapping("/curvepoint/add")
    public String showAddForm(CurvePoint curvePoint) {
        return "curvepoint/add";
    }
    
    /**
	 * Validates and saves a new curve point.
	 *
	 * @param curvePoint the CurvePoint object populated with form data. Validation is applied.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the curve point list if validation is successful,
	 * otherwise, it returns to the add form.
	 */
    @PostMapping("/curvepoint/validate")
    public String validateCurvePoint(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/curvepoint/add";
        }

        curveService.validate(curvePoint); 
        return "redirect:/curvepoint/list";
    }

    /**
   	 * Displays the form to update a curve point.
   	 *
   	 * @param id the ID of the curve point to modify, taken from the URL.
   	 * @param model the Model object to pass the curve point's data to the view.
   	 * @return the name of the view "curvepoint/update" which contains the form.
   	 */
    @GetMapping("/curvepoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("curvePoint", curveService.getCurvePointById(id));
        return "curvepoint/update";
    }

    /**
	 * Handles the form submission to update a curve point.
	 *
	 * @param id the ID of the curve point to update, taken from the URL.
	 * @param curvePoint the CurvePoint object with the new form data.
	 * @param result the object that holds the validation results.
	 * @param model the Model object.
	 * @return a redirect to the curve point list after the update.
	 */
    @PostMapping("curvepoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	curveService.update(curvePoint);
        return "redirect:/curvepoint/list";
    }

    /**
	 * Deletes a curve point from the database.
	 *
	 * @param id the ID of the curve point to delete, taken from the URL.
	 * @param model the Model object.
	 * @return a redirect to the curve point list after the deletion.
	 */
    @GetMapping("curvepoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
    	curveService.deleteCurvePoint(id);
        return "redirect:/curvepoint/list";
    }
}
