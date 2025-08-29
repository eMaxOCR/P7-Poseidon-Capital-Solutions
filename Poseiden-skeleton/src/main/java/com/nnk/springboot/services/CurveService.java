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
	 * Save CurvePoint into data base
	 * @param CurvePoint
	 * @return CurvePoint
	 * */
	public CurvePoint save(CurvePoint curvePoint){
		return curvePointRepository.save(curvePoint);
	}
	
	/**
	 * Find CurvePoint by id
	 * @param CurvePoint's Id
	 * @return CurvePoint
	 * */
	public CurvePoint getCurvePointById(Integer id){
		return curvePointRepository.getReferenceById(id);
	}
	
	/**
	 * Search all curve points
	 * @return CurvePoint List
	 * */
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
	 * Making Curve Point and put informations before saving.
	 * @param CurvePoint
	 * @return CurvePoint
	 * */
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
	 * Update Curve Point.
	 * @Param curvepoint
	 * */
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
	 * Delete Curve Point.
	 * @Param CurvePoint's ID
	 * */
	public void deleteCurvePoint(Integer id) {
		CurvePoint curvePointToDelete = getCurvePointById(id);
		curvePointRepository.deleteById(curvePointToDelete.getId());
	}
		
	
	
}
