package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	private TradeRepository tradeRepository;
	@Autowired
	private UserService userService;
		
	/**
	 * Saves a trade to the database.
	 *
	 * @param trade the Trade object to be saved.
	 * @return the saved Trade object.
	 */
	public Trade save(Trade trade){
		return tradeRepository.save(trade);
	}
	
	/**
	 * Finds a trade by its ID.
	 *
	 * @param id the ID of the trade to find.
	 * @return the found Trade object.
	 */
	public Trade getTradeById(Integer id){
		return tradeRepository.getReferenceById(id);
	}
	
	/**
	 * Finds all trades in the database.
	 *
	 * @return a list of all Trade objects.
	 */
	public List<Trade> getAllTrades(){
		return tradeRepository.findAll();
	}
	
	/**
	 * Prepares and validates a new trade before saving it.
	 * The method adds the creation date and the user's name.
	 *
	 * @param trade the Trade object to be validated and saved.
	 * @return the validated trade.
	 */
	public Trade validate(Trade trade){
		
		Trade newTrade = trade;
		LocalDateTime now = LocalDateTime.now();
		
		newTrade.setCreationDate(now);
		newTrade.setCreationName(userService.getCurrentUser().getFullname());

		return save(newTrade);
	}
	
	/**
	 * Updates the information of an existing trade.
	 * The method gets the current trade and updates some of its information
	 * with the new data from the provided trade.
	 *
	 * @param trade the Trade object with the new information.
	 */
	public void update(Trade trade){
								
		Trade currentTrade = getTradeById(trade.getTradeId());	//This trade have "old" informations
		LocalDateTime now = LocalDateTime.now();
		
		currentTrade.setAccount(trade.getAccount());
	    currentTrade.setType(trade.getType());
	    currentTrade.setBuyQuantity(trade.getBuyQuantity());
	    currentTrade.setSellQuantity(currentTrade.getSellQuantity());
	    currentTrade.setBuyPrice(currentTrade.getBuyPrice());
	    currentTrade.setSellPrice(currentTrade.getSellPrice());
	    currentTrade.setBenchmark(currentTrade.getBenchmark());
	    currentTrade.setTradeDate(currentTrade.getTradeDate());
	    currentTrade.setSecurity(currentTrade.getSecurity());
	    currentTrade.setStatus(currentTrade.getStatus());
	    currentTrade.setTrader(currentTrade.getTrader());
	    currentTrade.setBook(currentTrade.getBook());
	    currentTrade.setCreationName(currentTrade.getCreationName());
	    currentTrade.setCreationDate(currentTrade.getCreationDate());
	    currentTrade.setRevisionName(userService.getCurrentUser().getFullname());
	    currentTrade.setRevisionDate(now);
	    currentTrade.setDealName(currentTrade.getDealName());
	    currentTrade.setDealType(currentTrade.getDealType());
	    currentTrade.setSourceListId(currentTrade.getSourceListId());
	    currentTrade.setSide(currentTrade.getSide());

		//Save new informations
		save(currentTrade);
		
	}
	
	/**
	 * Deletes a trade from the database.
	 *
	 * @param id the ID of the trade to delete.
	 */
	public void deleteTrade(Integer id) {
		tradeRepository.deleteById(id);
	}
		
	
	
}
