package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

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
	
	@NotBlank(message = "Curve identification is mandatory")
	private Integer curveId;
	
	private LocalDateTime  asOfDate;
	
	private int term;
	
	@NotBlank(message = "Value is mandatory")
	private Double value;
	
	private LocalDateTime  creationDate;
	
	@ManyToOne //Many CurvePoint for in user
	private User user;

	
}
