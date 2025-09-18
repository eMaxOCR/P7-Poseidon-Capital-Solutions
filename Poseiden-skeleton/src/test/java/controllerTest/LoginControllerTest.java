package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserRepository userRepository;

	@MockitoBean
	private UserService userService;

	private User user;
	private List<User> userList;
	
	@BeforeEach
	public void setupTest() {
		user = new User();
		user.setId(1);
		user.setFullname("Test User");
		user.setUsername("testuser");
		user.setPassword("Password123!");
		user.setRole("USER");
		
		userList = new ArrayList<>();
		userList.add(user);
	}

	@Test
	public void loginTest() throws Exception {
	    mockMvc.perform(get("/app/login").with(csrf())) 
	        .andExpect(status().isOk())
	        .andExpect(view().name("login"));
	}

	@Test
	public void signupFormTest() throws Exception {
	    mockMvc.perform(get("/app/signup").with(csrf())) 
	        .andExpect(status().isOk())
	        .andExpect(view().name("signup"))
	        .andExpect(model().attributeExists("user"));
	}

	@Test
	public void signupTest() throws Exception {
	    mockMvc.perform(post("/app/signup")
	        .param("fullname", "Test User")
	        .param("username", "testuser")
	        .param("password", "Password123!")
	        .with(csrf()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidlist/list"));

	    verify(userService, times(1)).validate(any(User.class));
	}
	
	@Test
	public void signupWithErrorTest() throws Exception {
		mockMvc.perform(post("/app/signup")
				.param("fullname", "") // Un champ vide pour provoquer une erreur de validation
				.param("username", "testuser")
				.param("password", "Password123!")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("signup"))
				.andExpect(model().attributeHasErrors("user"));

		verify(userService, times(0)).validate(any(User.class));
	}

	@Test
	public void logoutTest() throws Exception {
	    mockMvc.perform(get("/app/logout").with(csrf())) // Ajoute le jeton CSRF à la requête
	        .andExpect(status().isOk())
	        .andExpect(view().name("login"));
	}

	@Test
	public void getAllUserArticlesTest() throws Exception {
		when(userRepository.findAll()).thenReturn(userList);
		
		mockMvc.perform(get("/app/secure/article-details").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list"))
				.andExpect(model().attributeExists("users"));

		verify(userRepository, times(1)).findAll();
	}
	
}