package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
	
//	/**
//	 * Searching all transactions from User that has been receiver or sender.
//	 * @param username.
//	 * */
//	@Query(value = "SELECT * FROM curvepoint t WHERE t.user_id = :user", nativeQuery = true)
//    public List<CurvePoint> findAllByUser(@Param("user") Integer id);
	
}
