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
	 * Save Trade into data base
	 * @param Trade
	 * @return Trade
	 * */
	public Trade save(Trade trade){
		return tradeRepository.save(trade);
	}
	
	/**
	 * Find Trade by id
	 * @param Trade
	 * @return Trade
	 * */
	public Trade getTradeById(Integer id){
		return tradeRepository.getReferenceById(id);
	}
	
	/**
	 * Search all Trade
	 * @return Trade list
	 * */
	public List<Trade> getAllTrades(){
		return tradeRepository.findAll();
	}
	
	/**
	 * Making rating and put informations before saving.
	 * @param Trade
	 * @return Trade
	 * */
	public Trade validate(Trade trade){
		
		Trade newTrade = trade;
		LocalDateTime now = LocalDateTime.now();
		
		newTrade.setCreationDate(now);
		newTrade.setCreationName(userService.getCurrentUser().getFullname());

		return save(newTrade);
	}
	
	/**
	 * Update Trade.
	 * @Param Trade
	 * */
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
	 * Delete rating
	 * @param Trade id
	 * */
	public void deleteTrade(Integer id) {
		tradeRepository.deleteById(id);
	}
		
	
	
}
