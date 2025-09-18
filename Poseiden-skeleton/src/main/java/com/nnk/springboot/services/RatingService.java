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
	 * Save Rating into data base
	 * */
	public Rating save(Rating rating){
		return ratingRepository.save(rating);
	}
	
	/**
	 * Find Rating by id
	 * */
	public Rating getRatingById(Integer id){
		return ratingRepository.getReferenceById(id);
	}
	
	/**
	 * Search all Rating
	 * */
	public List<Rating> getAllRatings(){
		return ratingRepository.findAll();
	}
	
	/**
	 * Making rating and put informations before saving.
	 * */
	public Rating validate(Rating rating){
		
		Rating newRating = new Rating();
		
		newRating.setFitchRating(rating.getFitchRating());
		newRating.setMoodysRating(rating.getMoodysRating());
		newRating.setOrderNumber(rating.getOrderNumber());
		newRating.setSandPRating(rating.getSandPRating());
		
		return save(rating);
	}
	
	/**
	 * Update Rating.
	 * @Param Rating
	 * */
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
	 * Delete rating
	 * */
	public void deleteRating(Integer id) {
		ratingRepository.deleteById(id);
	}
		
	
	
}
