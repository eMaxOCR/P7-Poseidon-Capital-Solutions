package com.nnk.springboot.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;


@Service
public class BidListService {
	
	@Autowired
	private BidListRepository bidRepository;
		
	/**
	 * Save Bid into data base
	 * */
	public Bid save(Bid bid){
		return bidRepository.save(bid);
	}
	
	/**
	 * Find Bid by id
	 * */
	public Bid getBidById(Integer id){
		return bidRepository.getReferenceById(id);
	}
	
	/**
	 * Search all Bid
	 * */
	public List<Bid> getAllBids(){
		return bidRepository.findAll();
	}
	
	/**
	 * Making bid and put informations before saving.
	 * */
	public Bid validate(Bid bid){
		
		Bid newBid = bid;
		
		
		return save(bid);
	}
	
	/**
	 * Update Bid.
	 * @Param Bid
	 * */
	public void update(Bid bid){
		Bid newBid = bid;								//This bid contain new informations
		Bid currentBid = getBidById(bid.getBidListId());	//This bid have "old" informations
		
		currentBid.setAccount(bid.getAccount());
		currentBid.setType(bid.getType());
		currentBid.setBidQuantity(bid.getBidQuantity());
		
		//Saving bid's new informations
		save(newBid);
		
	}
	
	/**
	 * Delete bid
	 * */
	public void deleteBid(Integer id) {
		bidRepository.deleteById(id);
	}
		
	
	
}
