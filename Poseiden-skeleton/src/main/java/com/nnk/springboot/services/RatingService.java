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
	
//	/**
//	 * Making Curve Point and put informations before saving.
//	 * */
//	public CurvePoint validate(CurvePoint curvePoint){
//		User currentUser = userService.getCurrentUser();
//		curvePoint.setUser(currentUser);
//		
//		//Calculate creation date time.
//		LocalDateTime now = LocalDateTime.now();
//		curvePoint.setCreationDate(now);
//		
//		//Calculate term date
//		curvePoint.setAsOfDate(now.plusHours(curvePoint.getTerm()));
//		
//		return save(curvePoint);
//	}
	
//	/**
//	 * Update Curve Point.
//	 * @Param curvepoint
//	 * */
//	public void update(CurvePoint curvePoint){
//		CurvePoint newCurvePointInfo = curvePoint;								//This curvePoint contain new informations
//		CurvePoint currentCurvePoint = getCurvePointById(curvePoint.getId());	//This curvePoint have "old" informations
//		
//		//Checking for curve id
//		if(newCurvePointInfo.getCurveId() != currentCurvePoint.getCurveId()) {
//			currentCurvePoint.setCurveId(curvePoint.getCurveId());
//		}
//		
//		//Checking for term
//		if(newCurvePointInfo.getTerm() != currentCurvePoint.getTerm()) {
//			currentCurvePoint.setAsOfDate(
//					currentCurvePoint.getCreationDate().plusHours(curvePoint.getTerm())
//					);
//			currentCurvePoint.setTerm(curvePoint.getTerm());
//		}
//		
//		//Checking for value
//				if(newCurvePointInfo.getValue() != currentCurvePoint.getValue()) {
//					currentCurvePoint.setValue(curvePoint.getValue());;
//				}
//		
//		//Saving curve point's new informations
//		save(currentCurvePoint);
//		
//	}
	
	
	
	public void deleteRating(Integer id) {
		ratingRepository.deleteById(id);
	}
		
	
	
}
