package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurveService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	
	
	/**
	 * Save CurvePoint into data base
	 * */
	public CurvePoint save(CurvePoint curvePoint){
		return curvePointRepository.save(curvePoint);
	}
	
	/**
	 * Find CurvePoint by id
	 * */
	public CurvePoint getCurvePointById(Integer id){
		return curvePointRepository.getReferenceById(id);
	}
	
	/**
	 * Search all curve points
	 * */
	public List<CurvePoint> getAllCurvePoint(){
		return curvePointRepository.findAll();
	}
	
	/**
	 * Search all curve points from user
	 * */
	public List<CurvePoint> getAllCurvePointFromUser(){
		User currentUser = userService.getCurrentUser();
		return curvePointRepository.findAllByUser(currentUser.getId());
	}
	
	/**
	 * Making Curve Point and put informations before saving.
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
		if(newCurvePointInfo.getCurveId() != currentCurvePoint.getCurveId()) {
			currentCurvePoint.setCurveId(curvePoint.getCurveId());
		}
		
		//Checking for term
		if(newCurvePointInfo.getTerm() != currentCurvePoint.getTerm()) {
			
			//Empty because of lack of business requirement
//			currentCurvePoint.setAsOfDate(
//					currentCurvePoint.getCreationDate().plusHours(curvePoint.getTerm())
//					);
			currentCurvePoint.setTerm(curvePoint.getTerm());
		}
		
		//Checking for value
				if(newCurvePointInfo.getValue() != currentCurvePoint.getValue()) {
					currentCurvePoint.setValue(curvePoint.getValue());;
				}
		
		//Saving curve point's new informations
		save(currentCurvePoint);
		
	}
	
	
	
	public void deleteCurvePoint(Integer id) {
		CurvePoint curvePointToDelete = getCurvePointById(id);
		curvePointRepository.deleteById(curvePointToDelete.getId());
	}
		
	
	
}
