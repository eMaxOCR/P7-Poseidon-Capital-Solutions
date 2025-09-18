package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurveService {
		
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	
	/**
	 * Saves a curve point to the database.
	 *
	 * @param curvePoint the CurvePoint object to be saved.
	 * @return the saved CurvePoint object.
	 */
	public CurvePoint save(CurvePoint curvePoint){
		return curvePointRepository.save(curvePoint);
	}
	
	/**
	 * Finds a curve point by its ID.
	 *
	 * @param id the ID of the curve point to find.
	 * @return the found CurvePoint object.
	 */
	public CurvePoint getCurvePointById(Integer id){
		return curvePointRepository.getReferenceById(id);
	}
	
	/**
	 * Finds all curve points in the database.
	 *
	 * @return a list of all CurvePoint objects.
	 */
	public List<CurvePoint> getAllCurvePoint(){
		return curvePointRepository.findAll();
	}
	
//	/**
//	 * Search all curve points from user
//	 * */
//	public List<CurvePoint> getAllCurvePointFromUser(){
//		User currentUser = userService.getCurrentUser();
//		return curvePointRepository.findAllByUser(currentUser.getId());
//	}
	
	/**
	 * Prepares and validates a new curve point before saving it.
	 *
	 * @param curvePoint the CurvePoint object to be validated and saved.
	 * @return the validated CurvePoint object.
	 */
	public CurvePoint validate(CurvePoint curvePoint){
//		User currentUser = userService.getCurrentUser();
//		curvePoint.setUser(currentUser);
		
		//Calculate creation date time.
		LocalDateTime now = LocalDateTime.now();
		curvePoint.setCreationDate(now);
		
//		//Calculate term date
//		curvePoint.setAsOfDate(now.plusHours(curvePoint.getTerm()));
		
		return save(curvePoint);
	}
	
	/**
	 * Updates the information of an existing curve point.
	 *
	 * @param curvePoint the CurvePoint object with the new information.
	 */
	public void update(CurvePoint curvePoint){
		CurvePoint newCurvePointInfo = curvePoint;								//This curvePoint contain new informations
		CurvePoint currentCurvePoint = getCurvePointById(curvePoint.getId());	//This curvePoint have "old" informations
		
		//Checking for curve id
		currentCurvePoint.setCurveId(curvePoint.getCurveId());
		//Checking for term
		currentCurvePoint.setTerm(curvePoint.getTerm());
		//Checking for value
		currentCurvePoint.setValue(curvePoint.getValue());
		
//		//Checking for term
//		if(newCurvePointInfo.getTerm() != currentCurvePoint.getTerm()) {
//			
//			//Empty because of lack of business requirement
////			currentCurvePoint.setAsOfDate(
////					currentCurvePoint.getCreationDate().plusHours(curvePoint.getTerm())
////					);
//			currentCurvePoint.setTerm(curvePoint.getTerm());
//		}
				
		//Saving curve point's new informations
		save(currentCurvePoint);
		
	}
	
	
	/**
	 * Deletes a curve point from the database using its ID.
	 *
	 * @param id the ID of the curve point to be deleted.
	 */
	public void deleteCurvePoint(Integer id) {
		CurvePoint curvePointToDelete = getCurvePointById(id);
		curvePointRepository.deleteById(curvePointToDelete.getId());
	}
		
	
	
}
