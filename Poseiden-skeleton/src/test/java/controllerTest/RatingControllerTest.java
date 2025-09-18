package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RatingService;
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

@WebMvcTest(RatingController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private RatingService ratingService;

	@MockitoBean
	private UserService userService;

	private Rating rating;
	private User user;
	private List<Rating> ratings;

	@BeforeEach
	public void setupTest() {
		ratings = new ArrayList<>();
		
		Rating rating1 = new Rating();
		rating1.setId(1);
		rating1.setFitchRating("Fitch A");
		rating1.setMoodysRating("Moodys B");
		rating1.setSandPRating("S&P C");
		rating1.setOrderNumber(10);
		ratings.add(rating1);

		user = new User();
		user.setFullname("Test User");

		rating = new Rating();
		rating.setId(1);
		rating.setFitchRating("Fitch A");
		rating.setMoodysRating("Moodys B");
		rating.setSandPRating("S&P C");
		rating.setOrderNumber(10);
	}

	@Test
	public void ratingListTest() throws Exception {
		when(ratingService.getAllRatings()).thenReturn(ratings);
		when(userService.getCurrentUser()).thenReturn(user);

		mockMvc.perform(get("/rating/list").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/list"))
				.andExpect(model().attributeExists("ratings"))
				.andExpect(model().attributeExists("remoteUser"));

		verify(ratingService, times(1)).getAllRatings();
		verify(userService, times(1)).getCurrentUser();
	}

	@Test
	public void addRatingFormTest() throws Exception {
		mockMvc.perform(get("/rating/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/add"))
				.andExpect(model().attributeExists("rating"));
	}

	@Test
	public void validateRatingTest() throws Exception {
		mockMvc.perform(post("/rating/validate")
				.param("fitchRating", "Fitch A")
				.param("moodysRating", "Moodys B")
				.param("sandPRating", "S&P C")
				.param("orderNumber", "10")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"));

		verify(ratingService, times(1)).validate(any(Rating.class));
	}

	@Test
	public void validateRatingWithErrorsTest() throws Exception {
	    mockMvc.perform(post("/rating/validate")
	            .param("fitchRating", "")
	            .param("moodysRating", "Moodys B")
	            .param("sandPRating", "S&P C")
	            .param("orderNumber", "10")
	            .with(csrf()))
	            .andExpect(status().isOk())
	            .andExpect(view().name("rating/add"))
	            .andExpect(model().attributeHasErrors("rating"));

	    verify(ratingService, times(0)).validate(any(Rating.class));
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		when(ratingService.getRatingById(any(Integer.class))).thenReturn(rating);

		mockMvc.perform(get("/rating/update/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"))
				.andExpect(model().attributeExists("rating"));

		verify(ratingService, times(1)).getRatingById(1);
	}

	@Test
	public void updateRatingTest() throws Exception {
		mockMvc.perform(post("/rating/update/1")
				.param("id", "1")
				.param("fitchRating", "Updated Fitch")
				.param("moodysRating", "Updated Moodys")
				.param("sandPRating", "Updated S&P")
				.param("orderNumber", "20")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"));

		verify(ratingService, times(1)).update(any(Rating.class));
	}
	
	@Test
	public void deleteRatingTest() throws Exception {
		doNothing().when(ratingService).deleteRating(any(Integer.class));

		mockMvc.perform(get("/rating/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"));

		verify(ratingService, times(1)).deleteRating(1);
	}
}