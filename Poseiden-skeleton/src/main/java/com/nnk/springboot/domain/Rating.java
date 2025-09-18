package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	@NotBlank
	String moodysRating;
	@NotBlank
	String sandPRating;
	@NotBlank
	String fitchRating;
	Integer orderNumber;
}
