package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {
	
	@Autowired
	private RuleNameRepository ruleNameRepository;

	
	/**
	 * Save RuleName into data base
	 * @param RuleName
	 * @return RuleName
	 * */
	public RuleName save(RuleName ruleName){
		return ruleNameRepository.save(ruleName);
	}
	
	/**
	 * Find RuleName by id
	 * @param id
	 * @return RuleName
	 * */
	public RuleName getRuleNameById(Integer id){
		return ruleNameRepository.getReferenceById(id);
	}
	
	/**
	 * Get all RuleName
	 * @return List of RuleName
	 * */
	public List<RuleName> getAllRuleName(){
		return ruleNameRepository.findAll();
	}
	
	/**
	 * Making RuleName and put informations before saving.
	 * @param RuleName 
	 * @return RuleName
	 * */
	public RuleName validate(RuleName ruleName){
		return save(ruleName);
	}
	
	/**
	 * Update RuleName.
	 * @Param RuleName
	 * */
	public void updateRuleName(RuleName ruleName){
		RuleName newRuleName = ruleName;								//This curvePoint contain new informations
		RuleName currentRuleName = getRuleNameById(ruleName.getId());	//This curvePoint have "old" informations

	    currentRuleName.setName(newRuleName.getName());
	    currentRuleName.setDescription(newRuleName.getDescription());
	    currentRuleName.setJson(newRuleName.getJson());
	    currentRuleName.setTemplate(newRuleName.getTemplate());
	    currentRuleName.setSqlPart(newRuleName.getSqlPart());
	    currentRuleName.setSqlStr(newRuleName.getSqlStr());

	    save(currentRuleName);
		
	}
	
	
	/**
	 * Delete RuleName
	 * @param id
	 * */
	public void deleteRuleName(Integer id) {
		ruleNameRepository.deleteById(id);
	}
		
	
	
}
