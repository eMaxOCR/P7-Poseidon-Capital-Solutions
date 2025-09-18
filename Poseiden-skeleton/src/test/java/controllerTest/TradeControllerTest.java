package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.TradeService;
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

@WebMvcTest(TradeController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class TradeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TradeService tradeService;

	@MockitoBean
	private UserService userService;

	private Trade trade;
	private User user;
	private List<Trade> trades;

	@BeforeEach
	public void setupTest() {
		trades = new ArrayList<>();
		
		trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("Account test");
		trade.setType("Type test");
		trade.setBuyQuantity(10.0);
		trade.setSellQuantity(10.0);
		trades.add(trade);

		user = new User();
		user.setFullname("Test User");
	}

	@Test
	public void tradeListTest() throws Exception {
		when(tradeService.getAllTrades()).thenReturn(trades);
		when(userService.getCurrentUser()).thenReturn(user);

		mockMvc.perform(get("/trade/list").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/list"))
				.andExpect(model().attributeExists("trades"))
				.andExpect(model().attributeExists("remoteUser"));

		verify(tradeService, times(1)).getAllTrades();
		verify(userService, times(1)).getCurrentUser();
	}

	@Test
	public void addTradeFormTest() throws Exception {
		mockMvc.perform(get("/trade/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"))
				.andExpect(model().attributeExists("trade"));
	}

	@Test
	public void validateTradeTest() throws Exception {
		mockMvc.perform(post("/trade/validate")
				.param("account", "Account test")
				.param("type", "Type test")
				.param("buyQuantity", "10.0")
				.param("sellQuantity", "10.0")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));

		verify(tradeService, times(1)).validate(any(Trade.class));
	}

	@Test
	public void validateTradeWithErrorsTest() throws Exception {
		mockMvc.perform(post("/trade/validate")
				.param("account", "")
				.param("type", "Type test")
				.param("buyQuantity", "10.0")
				.param("sellQuantity", "10.0")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"))
				.andExpect(model().attributeHasErrors("trade"));

		verify(tradeService, times(0)).validate(any(Trade.class));
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		when(tradeService.getTradeById(any(Integer.class))).thenReturn(trade);

		mockMvc.perform(get("/trade/update/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"))
				.andExpect(model().attributeExists("trade"));

		verify(tradeService, times(1)).getTradeById(1);
	}

	@Test
	public void updateTradeTest() throws Exception {
		mockMvc.perform(post("/trade/update/1")
				.param("tradeId", "1")
				.param("account", "Updated Account")
				.param("type", "Updated Type")
				.param("buyQuantity", "20.0")
				.param("sellQuantity", "20.0")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));

		verify(tradeService, times(1)).update(any(Trade.class));
	}
	
	@Test
	public void deleteTradeTest() throws Exception {
		doNothing().when(tradeService).deleteTrade(any(Integer.class));

		mockMvc.perform(get("/trade/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));

		verify(tradeService, times(1)).deleteTrade(1);
	}
}