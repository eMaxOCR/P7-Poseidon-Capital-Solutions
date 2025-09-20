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
	 * Saves a bid to the database.
	 *
	 * @param bid the Bid object to be saved.
	 * @return the saved Bid object.
	 */
	public Bid save(Bid bid){
		return bidRepository.save(bid);
	}
	
	/**
	 * Finds a Bid by its ID.
	 *
	 * @param id the ID of the Bid to find.
	 * @return the found Bid object.
	 */
	public Bid getBidById(Integer id){
		return bidRepository.getReferenceById(id);
	}
	
	/**
	 * Finds all Bids in the database.
	 *
	 * @return a list of all Bid objects.
	 */
	public List<Bid> getAllBids(){
		return bidRepository.findAll();
	}
	
	/**
	 * Validates and prepares a new Bid before saving it.
	 * It sets the creation date and the full name of the current user.
	 *
	 * @param bid the Bid object to be validated and saved.
	 * @return the validated Bid object.
	 */
	public Bid validate(Bid bid){
		
		LocalDateTime now = LocalDateTime.now();
		Bid newBid = bid;
		
		newBid.setCreationDate(now);
		newBid.setCreationName(userService.getCurrentUser().getFullname());
		
		return save(newBid);
	}
	
	/**
	 * Updates an existing Bid in the database.
	 * It takes the updated information and saves it to the existing bid.
	 * It also sets the revision date and the name of the user who made the change.
	 *
	 * @param bid the Bid object with the updated information.
	 */
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
	 * Deletes a Bid from the database using its ID.
	 *
	 * @param id the ID of the Bid to be deleted.
	 */
	public void deleteBid(Integer id) {
		bidRepository.deleteById(id);
	}
		
	
	
}
