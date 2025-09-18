package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc()
@EnableMethodSecurity 
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	private User adminUser;
	private User regularUser;
	private List<User> userList;

	@BeforeEach
	public void setupTest() {
		adminUser = new User();
		adminUser.setUsername("admin");
		adminUser.setRole("ADMIN");
		adminUser.setPassword("password123!");
		adminUser.setFullname("Admin User");

		regularUser = new User();
		regularUser.setUsername("user");
		regularUser.setRole("USER");
		regularUser.setPassword("password123!");
		regularUser.setFullname("Regular User");

		userList = new ArrayList<>();
		userList.add(adminUser);
		userList.add(regularUser);
	}

	@Test
	public void userListTest() throws Exception {
		when(userService.findAll()).thenReturn(userList);

		mockMvc.perform(get("/user/list").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list"))
				.andExpect(model().attributeExists("users"));

		verify(userService, times(1)).findAll();
	}

	@Test
	public void addUserFormTest() throws Exception {
		mockMvc.perform(get("/user/add").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("user/add"))
				.andExpect(model().attributeExists("user"));
	}

	@Test
	public void validateUserTest() throws Exception {
	    mockMvc.perform(post("/user/validate")
	            .with(user("admin").roles("ADMIN"))
	            .param("username", "testuser")
	            .param("password", "Password123!")
	            .param("fullname", "Test User")
	            .param("role", "USER")
	            .with(csrf()))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/user/list"));

	    verify(userService, times(1)).validate(any(User.class));
	}

	@Test
	public void validateUserWithErrorsTest() throws Exception {
	    mockMvc.perform(post("/user/validate")
	            .with(user("admin").roles("ADMIN"))
	            .param("username", "")
	            .param("password", "Password123!")
	            .param("fullname", "Test User")
	            .param("role", "USER")
	            .with(csrf()))
	            .andExpect(status().isOk())
	            .andExpect(view().name("/user/add"))
	            .andExpect(model().attributeHasErrors("user"));

	    verify(userService, times(0)).validate(any(User.class));
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		when(userService.findById(any(Integer.class))).thenReturn(Optional.of(regularUser));

		mockMvc.perform(get("/user/update/1").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("user/update"))
				.andExpect(model().attributeExists("user"));

		verify(userService, times(1)).findById(1);
	}

	@Test
	public void updateUserTest() throws Exception {
	    mockMvc.perform(post("/user/update/1")
	            .with(user("admin").roles("ADMIN")) // Adds an ADMIN user to the request
	            .param("id", "1")
	            .param("username", "updateduser")
	            .param("password", "NewPassword123!")
	            .param("fullname", "Updated User")
	            .param("role", "ADMIN")
	            .with(csrf()))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/user/list"));

	    verify(userService, times(1)).update(any(User.class));
	}
	
	@Test
	public void deleteUserTest() throws Exception {
		when(userService.findById(any(Integer.class))).thenReturn(Optional.of(regularUser));
		doNothing().when(userService).deleteUser(any(User.class));

		mockMvc.perform(get("/user/delete/1").with(user("admin").roles("ADMIN")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"));

		verify(userService, times(1)).findById(1);
		verify(userService, times(1)).deleteUser(any(User.class));
	}
}