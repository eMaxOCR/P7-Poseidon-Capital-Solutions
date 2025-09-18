package serviceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
	
	@Mock
	RatingRepository ratingRepository;
	
	@InjectMocks
	@Spy	//Test getRuleNameById without BDD.
	RatingService ratingService;
	
	private Rating rating;
	private Rating rating2;
	private Rating updatedRating;
	private List<Rating> ratingList = new ArrayList<>();
	
	@BeforeEach
	public void setupTest() {
		Rating setupRating = new Rating();
		setupRating.setId(1);
		setupRating.setFitchRating("Fitch A");
		setupRating.setMoodysRating("Moody's B");
		setupRating.setSandPRating("S&P C");
		setupRating.setOrderNumber(10);
		rating = setupRating;
		
		Rating setupUpdatedRating = new Rating();
		setupUpdatedRating.setId(1);
		setupUpdatedRating.setFitchRating("Fitch Updated");
		setupUpdatedRating.setMoodysRating("Moody's Updated");
		setupUpdatedRating.setSandPRating("S&P Updated");
		setupUpdatedRating.setOrderNumber(15);
		updatedRating = setupUpdatedRating;
		
		Rating secondRating = new Rating();
		secondRating.setId(2);
		secondRating.setFitchRating("Fitch B");
		rating2 = secondRating;
		
		ratingList.add(rating);
		ratingList.add(secondRating);
	}
	
	@Test
	public void saveTest() {
		// ARRANGE
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
		
		// ACT
		Rating savedRating = ratingService.save(rating);
		
		// ASSERT
		assertNotNull(savedRating);
		assertEquals(rating.getFitchRating(), savedRating.getFitchRating());
		verify(ratingRepository, times(1)).save(rating);
	}
	
	@Test
	public void getRatingByIdTest() {
		// ARRANGE
		when(ratingRepository.getReferenceById(any(Integer.class))).thenReturn(rating);
		
		// ACT
		Rating foundRating = ratingService.getRatingById(rating.getId());
		
		// ASSERT
		assertNotNull(foundRating);
		assertEquals(rating.getId(), foundRating.getId());
	}
	
	@Test
	public void getAllRatingsTest() {
		// ARRANGE
		when(ratingRepository.findAll()).thenReturn(ratingList);
		
		// ACT
		List<Rating> ratings = ratingService.getAllRatings();
		
		// ASSERT
		assertNotNull(ratings);
		assertEquals(2, ratings.size());
		assertEquals(rating.getId(), ratings.get(0).getId());
		assertEquals(rating2.getId(), ratings.get(1).getId());
	}
	
	@Test
	public void validateTest() {
		// ARRANGE
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
		
		// ACT
		Rating validatedRating = ratingService.validate(rating);
		
		// ASSERT
		assertNotNull(validatedRating);
		verify(ratingRepository, times(1)).save(rating);
	}
	
	@Test
	public void updateTest() {
		// ARRANGE
		when(ratingRepository.getReferenceById(any(Integer.class))).thenReturn(rating);
		when(ratingRepository.save(any(Rating.class))).thenReturn(updatedRating);
		
		// ACT
		ratingService.update(updatedRating);
		
		// ASSERT
		verify(ratingRepository, times(1)).getReferenceById(updatedRating.getId());
		verify(ratingRepository, times(1)).save(any(Rating.class));
	}
	
	@Test
	public void deleteTest() {
		// ARRANGE
		doNothing().when(ratingRepository).deleteById(any(Integer.class));
		
		// ACT
		ratingService.deleteRating(rating.getId());
		
		// ASSERT
		verify(ratingRepository, times(1)).deleteById(rating.getId());
	}
}