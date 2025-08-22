package com.nnk.springboot.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	
	private Integer curveId;
	
	private LocalDateTime  asOfDate;
	
	private int term;
	
	private Double value;
	
	private LocalDateTime  creationDate;
	
	@ManyToOne //Many CurvePoint for in user
	private User user;

	
}
