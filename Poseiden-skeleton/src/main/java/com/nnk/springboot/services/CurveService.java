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
	 * Search all curve points from user
	 * */
	public CurvePoint save(CurvePoint curvePoint){
		return curvePointRepository.save(curvePoint);
	}
	
	/**
	 * Search all curve points from user
	 * */
	public CurvePoint validate(CurvePoint curvePoint){
		
		//Calculate creation date time.
		LocalDateTime now = LocalDateTime.now();
		curvePoint.setCreationDate(now);
		
		//Calculate term date
		curvePoint.setAsOfDate(now.plusHours(curvePoint.getTerm()));
		
		return save(curvePoint);
	}
	
	/**
	 * Search all curve points from user
	 * */
	public List<CurvePoint> getAllCurvePointFromUser(){
		return curvePointRepository.findAll();
	}
	
	
	
}
