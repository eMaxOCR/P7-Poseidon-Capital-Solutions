package com.nnk.springboot.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {
    
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	
	@NotNull(message = "must not be null")
	private Integer curveId;
	
	private LocalDateTime  asOfDate;
	
	private Double term;
	
	@NotNull(message = "Value is mandatory")
	private Double value;
	
	private LocalDateTime  creationDate;
	
//	@ManyToOne //Many CurvePoint for in user
//	private User user;

	
}
