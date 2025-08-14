package com.nnk.springboot.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

//import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import java.sql.Timestamp;
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

	
}
