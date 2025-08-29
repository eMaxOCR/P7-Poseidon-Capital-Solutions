package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
	
	@Autowired
	private BidListRepository bidRepository;
	
	@Autowired
	private UserService userService;
	
		
	/**
	 * Save Bid into data base
	 * @param Bid
	 * @return Bid
	 * */
	public Bid save(Bid bid){
		return bidRepository.save(bid);
	}
	
	/**
	 * Find Bid by id
	 * @param Bid's ID (Integer)
	 * @return Bid
	 * */
	public Bid getBidById(Integer id){
		return bidRepository.getReferenceById(id);
	}
	
	/**
	 * Search all Bid
	 * @return Bid List
	 * */
	public List<Bid> getAllBids(){
		return bidRepository.findAll();
	}
	
	/**
	 * Making bid and put informations before saving.
	 * @param Bid
	 * @return Bid
	 * */
	public Bid validate(Bid bid){
		
		LocalDateTime now = LocalDateTime.now();
		Bid newBid = bid;
		
		bid.setCreationDate(now);
		bid.setCreationName(userService.getCurrentUser().getFullname());
		
		return save(bid);
	}
	
	/**
	 * Update Bid.
	 * @Param Bid
	 * */
	public void update(Bid bid){
		Bid newBid = bid;									//This bid contain new informations
		Bid currentBid = getBidById(bid.getBidListId());	//This bid have "old" informations
		LocalDateTime now = LocalDateTime.now();
		
		//Put updated informations
		currentBid.setAccount(bid.getAccount());
		currentBid.setType(bid.getType());
		currentBid.setBidQuantity(bid.getBidQuantity());
		
		//Put revision user's informations 
		currentBid.setRevisionDate(now);
		currentBid.setRevisionName(userService.getCurrentUser().getFullname());
		
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
