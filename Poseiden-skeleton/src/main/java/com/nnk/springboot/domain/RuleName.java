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
@Table(name = "rulename")
public class RuleName {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	
	@NotBlank(message="Name is mandatory")
	String name;
	String description;
	String json;
	String template;
	String sqlStr;
	String sqlPart;

}
