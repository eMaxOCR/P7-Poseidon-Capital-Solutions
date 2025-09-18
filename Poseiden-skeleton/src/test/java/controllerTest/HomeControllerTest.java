package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void homeTest() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	@Test
	public void adminHomeTest() throws Exception {
		mockMvc.perform(get("/admin/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bidList/list"));
	}
	
	@Test
	public void errorTest() throws Exception {
		mockMvc.perform(get("/404"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void internalServerErrorTest() throws Exception {
		mockMvc.perform(get("/500"))
			.andExpect(status().isInternalServerError());
	}
}