package com.nnk.springboot.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
		
	/**
	 * Saves a rating to the database.
	 *
	 * @param rating the Rating object to be saved.
	 * @return the saved Rating object.
	 */
	public Rating save(Rating rating){
		return ratingRepository.save(rating);
	}
	
	/**
	 * Finds a rating by its ID.
	 *
	 * @param id the ID of the rating to find.
	 * @return the found Rating object.
	 */
	public Rating getRatingById(Integer id){
		return ratingRepository.getReferenceById(id);
	}
	
	/**
	 * Finds all ratings in the database.
	 *
	 * @return a list of all Rating objects.
	 */
	public List<Rating> getAllRatings(){
		return ratingRepository.findAll();
	}
	
	/**
	 * Prepares a rating's information before saving it.
	 *
	 * @param rating the Rating object to be validated and saved.
	 * @return the validated Rating object.
	 */
	public Rating validate(Rating rating){
		
		Rating newRating = new Rating();
		
		newRating.setFitchRating(rating.getFitchRating());
		newRating.setMoodysRating(rating.getMoodysRating());
		newRating.setOrderNumber(rating.getOrderNumber());
		newRating.setSandPRating(rating.getSandPRating());
		
		return save(rating);
	}
	
	/**
	 * Updates the information of an existing rating.
	 *
	 * @param rating the Rating object with the new information.
	 */
	public void update(Rating rating){
		Rating newRating = rating;								//This rating contain new informations
		Rating currentRating = getRatingById(rating.getId());	//This rating have "old" informations
		
		currentRating.setFitchRating(newRating.getFitchRating());
		currentRating.setMoodysRating(newRating.getMoodysRating());
		currentRating.setSandPRating(newRating.getSandPRating());
		currentRating.setOrderNumber(newRating.getOrderNumber());
		
		//Saving rating's new informations
		save(currentRating);
		
	}
	
	/**
	 * Deletes a rating from the database using its ID.
	 *
	 * @param id the ID of the rating to delete.
	 */
	public void deleteRating(Integer id) {
		ratingRepository.deleteById(id);
	}
		
	
	
}
