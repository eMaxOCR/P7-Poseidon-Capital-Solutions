package com.nnk.springboot.domain;

//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
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
@Table(name = "rulename")
public class RuleName {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	String name;
	String description;
	String json;
	String template;
	String sqlStr;
	String sqlPart;

}
