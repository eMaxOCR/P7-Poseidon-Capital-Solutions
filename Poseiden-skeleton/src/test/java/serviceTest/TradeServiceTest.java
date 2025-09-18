package serviceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeServiceTest {

	@Mock
	TradeRepository tradeRepository;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	@Spy	//Test getRuleNameById without BDD.
	TradeService tradeService;
	
	private Trade trade;
	private Trade trade2;
	private Trade updatedTrade;
	private List<Trade> tradeList = new ArrayList<>();

	@BeforeEach
	public void setupTest() {
		Trade setupTrade = new Trade();
		setupTrade.setTradeId(1);
		setupTrade.setAccount("Account Test");
		setupTrade.setType("Type Test");
		setupTrade.setBuyQuantity(10.0);
		setupTrade.setSellQuantity(20.0);
		setupTrade.setBuyPrice(50.0);
		setupTrade.setSellPrice(55.0);
		setupTrade.setBenchmark("Benchmark Test");
		setupTrade.setTradeDate(LocalDateTime.now());
		setupTrade.setSecurity("Security Test");
		setupTrade.setStatus("Status Test");
		setupTrade.setTrader("Trader Test");
		setupTrade.setBook("Book Test");
		setupTrade.setCreationName("Creator Test");
		setupTrade.setCreationDate(LocalDateTime.now());
		setupTrade.setRevisionName("Revision Test");
		setupTrade.setRevisionDate(LocalDateTime.now());
		setupTrade.setDealName("Deal Test");
		setupTrade.setDealType("Deal Type Test");
		setupTrade.setSourceListId("Source ID");
		setupTrade.setSide("Side Test");
		trade = setupTrade;
		
		Trade setupUpdatedTrade = new Trade();
		setupUpdatedTrade.setTradeId(1);
		setupUpdatedTrade.setAccount("Updated Account");
		setupUpdatedTrade.setType("Updated Type");
		setupUpdatedTrade.setBuyQuantity(15.0);
		updatedTrade = setupUpdatedTrade;
		
		Trade secondTrade = new Trade();
		secondTrade.setTradeId(2);
		secondTrade.setAccount("Account 2");
		secondTrade.setType("Type 2");
		secondTrade.setBuyQuantity(25.0);
		trade2 = secondTrade;
		
		tradeList.add(setupTrade);
		tradeList.add(secondTrade);
	}

	@Test
	public void saveTradeTest() {
		// ARRANGE
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
		
		// ACT
		Trade savedTrade = tradeService.save(trade);
		
		// ASSERT
		assertNotNull(savedTrade);
		assertEquals(trade.getAccount(), savedTrade.getAccount());
		verify(tradeRepository, times(1)).save(trade);
	}

	@Test
	public void getTradeByIdTest() {
		// ARRANGE
		when(tradeRepository.getReferenceById(any(Integer.class))).thenReturn(trade);
		
		// ACT
		Trade foundTrade = tradeService.getTradeById(trade.getTradeId());
		
		// ASSERT
		assertNotNull(foundTrade);
		assertEquals(trade.getTradeId(), foundTrade.getTradeId());
	}
	
	@Test
	public void getAllTradesTest() {
		// ARRANGE
		when(tradeRepository.findAll()).thenReturn(tradeList);
		
		// ACT
		List<Trade> trades = tradeService.getAllTrades();
		
		// ASSERT
		assertNotNull(trades);
		assertEquals(2, trades.size());
		assertEquals(trade.getTradeId(), trades.get(0).getTradeId());
		assertEquals(trade2.getTradeId(), trades.get(1).getTradeId());
	}
	
	@Test
	public void validateTest() {
		// ARRANGE
		User mockUser = new User();
		mockUser.setFullname("Test User");
		
		when(userService.getCurrentUser()).thenReturn(mockUser);
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
		
		// ACT
		Trade validatedTrade = tradeService.validate(trade);
		
		// ASSERT
		assertNotNull(validatedTrade.getCreationDate());
		assertEquals("Test User", validatedTrade.getCreationName());
		verify(tradeRepository, times(1)).save(trade);
	}
	
	@Test
	public void updateTest() {
		// ARRANGE
		User mockUser = new User();
		mockUser.setFullname("Test User");
		
		when(tradeRepository.getReferenceById(any(Integer.class))).thenReturn(trade);
		when(tradeRepository.save(any(Trade.class))).thenReturn(updatedTrade);
		when(userService.getCurrentUser()).thenReturn(mockUser);
		
		// ACT
		tradeService.update(updatedTrade);
		
		// ASSERT
		verify(tradeRepository, times(1)).getReferenceById(updatedTrade.getTradeId());
		verify(tradeRepository, times(1)).save(any(Trade.class));
	}
	
	@Test
	public void deleteTest() {
		// ARRANGE
		doNothing().when(tradeRepository).deleteById(any(Integer.class));
		
		// ACT
		tradeService.deleteTrade(trade.getTradeId());
		
		// ASSERT
		verify(tradeRepository, times(1)).deleteById(trade.getTradeId());
	}
}