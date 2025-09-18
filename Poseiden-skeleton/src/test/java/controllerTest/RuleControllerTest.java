package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.RuleController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RuleController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class RuleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private RuleNameService ruleNameService;

	@MockitoBean
	private UserService userService;

	private RuleName ruleName;
	private User user;
	private List<RuleName> ruleNames;

	@BeforeEach
	public void setupTest() {
		ruleNames = new ArrayList<>();
		
		RuleName ruleName1 = new RuleName();
		ruleName1.setId(1);
		ruleName1.setName("Rule Name");
		ruleName1.setDescription("Description");
		ruleName1.setJson("JSON");
		ruleName1.setTemplate("Template");
		ruleName1.setSqlStr("SQL");
		ruleName1.setSqlPart("Part");
		ruleNames.add(ruleName1);

		user = new User();
		user.setFullname("Test User");

		ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setName("Rule Name");
		ruleName.setDescription("Description");
		ruleName.setJson("JSON");
		ruleName.setTemplate("Template");
		ruleName.setSqlStr("SQL");
		ruleName.setSqlPart("Part");
	}

	@Test
	public void ruleListTest() throws Exception {
		when(ruleNameService.getAllRuleName()).thenReturn(ruleNames);
		when(userService.getCurrentUser()).thenReturn(user);

		mockMvc.perform(get("/rulename/list").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("rulename/list"))
				.andExpect(model().attributeExists("ruleNames"))
				.andExpect(model().attributeExists("remoteUser"));

		verify(ruleNameService, times(1)).getAllRuleName();
		verify(userService, times(1)).getCurrentUser();
	}

	@Test
	public void addRuleFormTest() throws Exception {
		mockMvc.perform(get("/rulename/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("rulename/add"))
				.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	public void validateRuleNameTest() throws Exception {
		mockMvc.perform(post("/rulename/validate")
				.param("name", "Rule Name")
				.param("description", "Description")
				.param("json", "JSON")
				.param("template", "Template")
				.param("sqlStr", "SQL")
				.param("sqlPart", "Part")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rulename/list"));

		verify(ruleNameService, times(1)).validate(any(RuleName.class));
	}

	@Test
	public void validateRuleNameWithErrorsTest() throws Exception {
		mockMvc.perform(post("/rulename/validate")
				.param("name", "") // Un champ vide pour provoquer une erreur
				.param("description", "Description")
				.param("json", "JSON")
				.param("template", "Template")
				.param("sqlStr", "SQL")
				.param("sqlPart", "Part")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("rulename/add"))
				.andExpect(model().attributeHasErrors("ruleName"));

		verify(ruleNameService, times(0)).validate(any(RuleName.class));
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		when(ruleNameService.getRuleNameById(any(Integer.class))).thenReturn(ruleName);

		mockMvc.perform(get("/rulename/update/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("rulename/update"))
				.andExpect(model().attributeExists("ruleName"));

		verify(ruleNameService, times(1)).getRuleNameById(1);
	}

	@Test
	public void updateRuleNameTest() throws Exception {
		mockMvc.perform(post("/rulename/update/1")
				.param("id", "1")
				.param("name", "Updated Rule")
				.param("description", "Updated Description")
				.param("json", "Updated JSON")
				.param("template", "Updated Template")
				.param("sqlStr", "Updated SQL")
				.param("sqlPart", "Updated Part")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rulename/list"));

		verify(ruleNameService, times(1)).updateRuleName(any(RuleName.class));
	}
	
	@Test
	public void deleteRuleNameTest() throws Exception {
		doNothing().when(ruleNameService).deleteRuleName(any(Integer.class));

		mockMvc.perform(get("/rulename/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rulename/list"));

		verify(ruleNameService, times(1)).deleteRuleName(1);
	}
}