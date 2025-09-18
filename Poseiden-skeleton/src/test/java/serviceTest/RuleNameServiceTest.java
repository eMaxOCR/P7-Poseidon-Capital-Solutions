package serviceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {
	
	@Mock
	RuleNameRepository ruleNameRepository;
	
	@InjectMocks
	@Spy	//Test getRuleNameById without BDD.
	RuleNameService ruleNameService;
	
	public RuleName rule;
	public RuleName rule2;
	public List<RuleName> ruleNameList = new ArrayList<>();
	
	@BeforeEach
	public void setupTest() {
		RuleName ruleSetup = new RuleName();
		ruleSetup.setId(1);
		ruleSetup.setName("Rule Name");
		ruleSetup.setDescription("Description");
		ruleSetup.setJson("Json");
		ruleSetup.setSqlPart("SQL Part");
		ruleSetup.setSqlStr("SQL");
		ruleSetup.setTemplate("Template");
		rule=ruleSetup;
		
		RuleName ruleSetup2 = new RuleName();
		ruleSetup2.setId(2);
		ruleSetup2.setName("Rule Name2");
		ruleSetup2.setDescription("Description2");
		ruleSetup2.setJson("Json2");
		ruleSetup2.setSqlPart("SQL Part2");
		ruleSetup2.setSqlStr("SQL2");
		ruleSetup2.setTemplate("Template2");
		rule2=ruleSetup2;
		
		ruleNameList.add(ruleSetup);
		ruleNameList.add(ruleSetup2);
	}

	@Test
	public void saveRuleTest() {
		//ARRANGE
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rule);
		//ACT
		RuleName newRuleName = ruleNameService.save(rule);
		//ASSERT
		assertEquals(newRuleName.getId(),rule.getId());
		verify(ruleNameRepository, times(1)).save(rule);
	}
	
	@Test
	public void getRuleNameByIdTest() {
		when(ruleNameRepository.getReferenceById(anyInt())).thenReturn(rule);
		RuleName ruleNameToFind = ruleNameService.getRuleNameById(1);
		assertEquals(ruleNameToFind.getId(),rule.getId());
	}
	
	@Test
	public void getAllRuleNameTest() {
		//ARRANGE
		when(ruleNameRepository.findAll()).thenReturn(ruleNameList);
		//ACT
		List<RuleName> ruleNameListTest = ruleNameService.getAllRuleName();
		//ASSERT
		assertEquals(2, ruleNameListTest.size());
		assertEquals(1, ruleNameListTest.get(0).getId());
		assertEquals(2, ruleNameListTest.get(1).getId());
		
	}
	
	@Test
	public void validateTest() {
		//ARRANGE
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rule);
		//ACT
		RuleName newRuleName = ruleNameService.validate(rule);
		//ASSERT
		assertNotNull(newRuleName);
		verify(ruleNameRepository, times(1)).save(rule);
		
		
	}
	
	@Test
	public void updateRuleNameTest() {
		//ARRANGE
		doReturn(rule).when(ruleNameService).getRuleNameById(rule.getId());
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(rule2);
		//ACT
		RuleName updatedRuleName = ruleNameService.updateRuleName(rule);
		//ASSERT
		assertNotNull(updatedRuleName);
		verify(ruleNameRepository, times(1)).save(rule);
		assertEquals(updatedRuleName, rule2);
		
		
	}
	
	@Test
	public void deleteTest() {
		//ARRANGE
		//ACT
		ruleNameService.deleteRuleName(rule.getId());
		//ASSERT
		verify(ruleNameRepository, times(1)).deleteById(rule.getId());
		
		
	}
	

}
