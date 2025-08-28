package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	
	@Autowired
	private UserService userService;
	
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
		
		if(newRating.getFitchRating() != currentRating.getFitchRating()) {
			currentRating.setFitchRating(newRating.getFitchRating());
		}
		
		if(newRating.getMoodysRating() != currentRating.getMoodysRating()) {
			currentRating.setMoodysRating(newRating.getMoodysRating());
		}
		
		if(newRating.getSandPRating() != currentRating.getSandPRating()) {
			currentRating.setSandPRating(newRating.getSandPRating());
		}
		
		if(newRating.getOrderNumber() != currentRating.getOrderNumber()) {
			currentRating.setOrderNumber(newRating.getOrderNumber());
		}
		
		//Saving rating's new informations
		save(currentRating);
		
	}
	
	
	
	public void deleteRating(Integer id) {
		ratingRepository.deleteById(id);
	}
		
	
	
}
