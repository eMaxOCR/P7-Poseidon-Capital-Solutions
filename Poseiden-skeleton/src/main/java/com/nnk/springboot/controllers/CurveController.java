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
//import javax.validation.Valid;

@Controller
public class CurveController {

	@Autowired
	private CurveService curveService;
	@Autowired
	private UserService userService;

    @RequestMapping("/curvepoint/list")
    public String curvePointList(Model model)
    {
        model.addAttribute("curvePoints", curveService.getAllCurvePoint());
        model.addAttribute("remoteUser", userService.getCurrentUser());

        return "curvepoint/list";
    }

    @GetMapping("/curvepoint/add")
    public String showAddForm(CurvePoint bid) {
        return "curvepoint/add";
    }

    @PostMapping("/curvepoint/validate")
    public String validateCurvePoint(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/curvepoint/add";
        }

        curveService.validate(curvePoint); 
        return "redirect:/curvepoint/list";
    }

    @GetMapping("/curvepoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("curvePoint", curveService.getCurvePointById(id));
        return "curvepoint/update";
    }

    @PostMapping("curvepoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	curveService.update(curvePoint);
        return "redirect:/curvepoint/list";
    }

    @GetMapping("curvepoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
    	curveService.deleteCurvePoint(id);
        return "redirect:/curvepoint/list";
    }
}
