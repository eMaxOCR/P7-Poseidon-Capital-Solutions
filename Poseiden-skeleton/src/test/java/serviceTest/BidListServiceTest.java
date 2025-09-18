package serviceTest;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

	@Mock
	BidListRepository bidRepository;

	@Mock
	UserService userService;
	
	@InjectMocks
	@Spy	//Test getRuleNameById without BDD.
	BidListService bidListService;
	
	private Bid bid;
	private Bid bid2;
	private Bid updatedBid;
	private List<Bid> bidList = new ArrayList<>();
	
	@BeforeEach
	public void setupTest() {
		Bid setupBid = new Bid();
		setupBid.setBidListId(1);
		setupBid.setAccount("Account Test");
		setupBid.setType("Type Test");
		setupBid.setBidQuantity(10.0);
		setupBid.setCreationDate(LocalDateTime.now());
		setupBid.setCreationName("Creator Test");
		setupBid.setRevisionDate(LocalDateTime.now());
		setupBid.setRevisionName("Revision Test");
		bid = setupBid;
		
		Bid setupUpdatedBid = new Bid();
		setupUpdatedBid.setBidListId(1);
		setupUpdatedBid.setAccount("Updated Account");
		setupUpdatedBid.setType("Updated Type");
		setupUpdatedBid.setBidQuantity(15.0);
		updatedBid = setupUpdatedBid;
		
		Bid secondBid = new Bid();
		secondBid.setBidListId(2);
		secondBid.setAccount("Account 2");
		secondBid.setType("Type 2");
		secondBid.setBidQuantity(20.0);
		bid2 = secondBid;
		
		bidList.add(bid);
		bidList.add(secondBid);
	}
	
	@Test
	public void saveTest() {
		// ARRANGE
		when(bidRepository.save(any(Bid.class))).thenReturn(bid);
		
		// ACT
		Bid savedBid = bidListService.save(bid);
		
		// ASSERT
		assertNotNull(savedBid);
		assertEquals(bid.getAccount(), savedBid.getAccount());
		verify(bidRepository, times(1)).save(bid);
	}
	
	@Test
	public void getBidByIdTest() {
		// ARRANGE
		when(bidRepository.getReferenceById(any(Integer.class))).thenReturn(bid);
		
		// ACT
		Bid foundBid = bidListService.getBidById(bid.getBidListId());
		
		// ASSERT
		assertNotNull(foundBid);
		assertEquals(bid.getBidListId(), foundBid.getBidListId());
	}
	
	@Test
	public void getAllBidsTest() {
		// ARRANGE
		when(bidRepository.findAll()).thenReturn(bidList);
		
		// ACT
		List<Bid> bids = bidListService.getAllBids();
		
		// ASSERT
		assertNotNull(bids);
		assertEquals(2, bids.size());
		assertEquals(bid.getBidListId(), bids.get(0).getBidListId());
		assertEquals(bid2.getBidListId(), bids.get(1).getBidListId());
	}
	
	@Test
	public void validateTest() {
		// ARRANGE
		User mockUser = new User();
		mockUser.setFullname("Test User");
		
		when(userService.getCurrentUser()).thenReturn(mockUser);
		when(bidRepository.save(any(Bid.class))).thenReturn(bid);
		
		// ACT
		Bid validatedBid = bidListService.validate(bid);
		
		// ASSERT
		assertNotNull(validatedBid);
		assertEquals("Test User", validatedBid.getCreationName());
		assertNotNull(validatedBid.getCreationDate());
		verify(bidRepository, times(1)).save(bid);
	}
	
	@Test
	public void updateTest() {
		// ARRANGE
		User mockUser = new User();
		mockUser.setFullname("Test User");

		when(bidRepository.getReferenceById(any(Integer.class))).thenReturn(bid);
		when(bidRepository.save(any(Bid.class))).thenReturn(updatedBid);
		when(userService.getCurrentUser()).thenReturn(mockUser);
		
		// ACT
		bidListService.update(updatedBid);
		
		// ASSERT
		verify(bidRepository, times(1)).getReferenceById(updatedBid.getBidListId());
		verify(bidRepository, times(1)).save(updatedBid);
	}
	
	@Test
	public void deleteBidTest() {
		// ARRANGE
		doNothing().when(bidRepository).deleteById(any(Integer.class));
		
		// ACT
		bidListService.deleteBid(bid.getBidListId());
		
		// ASSERT
		verify(bidRepository, times(1)).deleteById(bid.getBidListId());
	}
}