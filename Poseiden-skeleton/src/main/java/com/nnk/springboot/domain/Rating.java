package com.nnk.springboot.domain;

//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	String moodysRating;
	String sandPRating;
	String fitchRating;
	Integer orderNumber;
}
