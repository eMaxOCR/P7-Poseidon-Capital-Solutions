package com.nnk.springboot.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RuleNameService {
	
	//@Autowired
	private final RuleNameRepository ruleNameRepository;

	
	/**
	 * Saves a rule to the database.
	 *
	 * @param ruleName the RuleName object to be saved.
	 * @return the saved RuleName object.
	 */
	public RuleName save(RuleName ruleName){
		return ruleNameRepository.save(ruleName);
	}
	
	/**
	 * Finds a rule by its ID.
	 *
	 * @param id the ID of the rule to find.
	 * @return the found RuleName object.
	 */
	public RuleName getRuleNameById(Integer id){
		return ruleNameRepository.getReferenceById(id);
	}
	
	/**
	 * Finds all rules in the database.
	 *
	 * @return a list of all RuleName objects.
	 */
	public List<RuleName> getAllRuleName(){
		return ruleNameRepository.findAll();
	}
	
	/**
	 * Prepares and validates a new rule before saving it.
	 *
	 * @param ruleName the RuleName object to be validated.
	 * @return the validated RuleName object.
	 */
	public RuleName validate(RuleName ruleName){
		return save(ruleName);
	}
	
	/**
	 * Updates an existing rule in the database.
	 *
	 * @param ruleName the RuleName object with the new information.
	 * @return the updated and saved rule.
	 */
	public RuleName updateRuleName(RuleName ruleName){
		RuleName newRuleName = ruleName;								//This curvePoint contain new informations
		RuleName currentRuleName = getRuleNameById(ruleName.getId());	//This curvePoint have "old" informations

	    currentRuleName.setName(newRuleName.getName());
	    currentRuleName.setDescription(newRuleName.getDescription());
	    currentRuleName.setJson(newRuleName.getJson());
	    currentRuleName.setTemplate(newRuleName.getTemplate());
	    currentRuleName.setSqlPart(newRuleName.getSqlPart());
	    currentRuleName.setSqlStr(newRuleName.getSqlStr());

	    return save(currentRuleName);
		
	}
	
	
	/**
	 * Deletes a rule from the database using its ID.
	 *
	 * @param id the ID of the rule to delete.
	 */
	public void deleteRuleName(Integer id) {
		ruleNameRepository.deleteById(id);
	}
		
	
	
}
