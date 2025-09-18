package controllerTest;

import com.nnk.springboot.Application;
import com.nnk.springboot.controllers.BidController;
import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(BidController.class)
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;
    
    @MockitoBean
    private UserService userService;
    
    private Bid bid;
    private User user;
    private List<Bid> bids;
    
    @BeforeEach
    public void setupTest() {
    	bids = new ArrayList<>();
    	
    	Bid bid1 = new Bid();
    	bid1.setBidListId(1);
    	bid1.setAccount("Account 1");
    	bid1.setType("Type 1");
    	bid1.setBidQuantity(10.0);
    	bids.add(bid1);
    	
    	user = new User();
    	user.setFullname("Test User");
    	
    	bid = new Bid();
    	bid.setBidListId(1);
    	bid.setAccount("Account");
    	bid.setType("Type");
    	bid.setBidQuantity(10.0);
    }

    @Test
    public void homeTest() throws Exception {
        when(bidListService.getAllBids()).thenReturn(bids);
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/bidlist/list").with(csrf())) 
                .andExpect(status().isOk())
                .andExpect(view().name("bidlist/list"))
                .andExpect(model().attributeExists("bids"))
                .andExpect(model().attributeExists("remoteUser"));
                
        verify(bidListService, times(1)).getAllBids();
        verify(userService, times(1)).getCurrentUser();
    }
    
    @Test
    public void addBidFormTest() throws Exception {
        mockMvc.perform(get("/bidlist/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidlist/add"))
                .andExpect(model().attributeExists("bid"));
    }

    @Test
    public void validateTest() throws Exception {
        mockMvc.perform(post("/bidlist/validate")
                .param("account", "Account")
                .param("type", "Type")
                .param("bidQuantity", "10.0")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidlist/list"));

        verify(bidListService, times(1)).validate(any(Bid.class));
    }
    
    @Test
    public void validateWithErrorsTest() throws Exception {
    	mockMvc.perform(post("/bidlist/validate")
                .param("account", "")
                .param("type", "Type")
                .param("bidQuantity", "10.0")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidlist/add"))
                .andExpect(model().attributeHasErrors("bid"));
                
    	verify(bidListService, times(0)).validate(any(Bid.class));
    }

    @Test
    public void showUpdateFormTest() throws Exception {
        when(bidListService.getBidById(any(Integer.class))).thenReturn(bid);

        mockMvc.perform(get("/bidlist/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidlist/update"))
                .andExpect(model().attributeExists("bid"));

        verify(bidListService, times(1)).getBidById(1);
    }

    @Test
    public void updateBidTest() throws Exception {
        mockMvc.perform(post("/bidlist/update/1")
                .param("bidListId", "1")
                .param("account", "Updated Account")
                .param("type", "Updated Type")
                .param("bidQuantity", "20.0")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidlist/list"));

        verify(bidListService, times(1)).update(any(Bid.class));
    }

    @Test
    public void deleteBidTest() throws Exception {
        doNothing().when(bidListService).deleteBid(any(Integer.class));

        mockMvc.perform(get("/bidlist/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidlist/list"));

        verify(bidListService, times(1)).deleteBid(1);
    }
}