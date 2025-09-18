package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.CurveService;
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

@WebMvcTest(CurveController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class CurveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CurveService curveService;

	@MockitoBean
	private UserService userService;

	private CurvePoint curvePoint;
	private User user;
	private List<CurvePoint> curvePoints;

	@BeforeEach
	public void setupTest() {
		curvePoints = new ArrayList<>();

		CurvePoint curvePoint1 = new CurvePoint();
		curvePoint1.setId(1);
		curvePoint1.setCurveId(10);
		curvePoint1.setTerm(10.0);
		curvePoint1.setValue(20.0);
		curvePoints.add(curvePoint1);

		user = new User();
		user.setFullname("Test User");

		curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10.0);
		curvePoint.setValue(20.0);
	}

	@Test
	public void curvePointListTest() throws Exception {
		when(curveService.getAllCurvePoint()).thenReturn(curvePoints);
		when(userService.getCurrentUser()).thenReturn(user);

		mockMvc.perform(get("/curvepoint/list").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("curvepoint/list"))
				.andExpect(model().attributeExists("curvePoints"))
				.andExpect(model().attributeExists("remoteUser"));

		verify(curveService, times(1)).getAllCurvePoint();
		verify(userService, times(1)).getCurrentUser();
	}

	@Test
	public void showAddFormTest() throws Exception {
		mockMvc.perform(get("/curvepoint/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvepoint/add"))
				.andExpect(model().attributeExists("curvePoint"));
	}

	@Test
	public void validateCurvePointTest() throws Exception {
		mockMvc.perform(post("/curvepoint/validate")
				.param("curveId", "10")
				.param("term", "10.0")
				.param("value", "20.0")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvepoint/list"));

		verify(curveService, times(1)).validate(any(CurvePoint.class));
	}

	@Test
	public void validateCurvePointWithErrorsTest() throws Exception {
		mockMvc.perform(post("/curvepoint/validate")
				.param("curveId", "")
				.param("term", "10.0")
				.param("value", "20.0")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("/curvepoint/add"))
				.andExpect(model().attributeHasErrors("curvePoint"));

		verify(curveService, times(0)).validate(any(CurvePoint.class));
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		when(curveService.getCurvePointById(any(Integer.class))).thenReturn(curvePoint);

		mockMvc.perform(get("/curvepoint/update/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvepoint/update"))
				.andExpect(model().attributeExists("curvePoint"));

		verify(curveService, times(1)).getCurvePointById(1);
	}

	@Test
	public void updateCurvePointTest() throws Exception {
		mockMvc.perform(post("/curvepoint/update/1")
				.param("id", "1")
				.param("curveId", "10")
				.param("term", "15.0")
				.param("value", "25.0")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvepoint/list"));

		verify(curveService, times(1)).update(any(CurvePoint.class));
	}
	
	@Test
	public void deleteCurvePointTest() throws Exception {
		doNothing().when(curveService).deleteCurvePoint(any(Integer.class));

		mockMvc.perform(get("/curvepoint/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvepoint/list"));

		verify(curveService, times(1)).deleteCurvePoint(1);
	}
}