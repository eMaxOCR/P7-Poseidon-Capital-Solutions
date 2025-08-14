package com.nnk.springboot.domain;

import java.security.Timestamp;

//import org.springframework.beans.factory.annotation.Required;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import javax.persistence.*;
//import javax.validation.constraints.Digits;
//import javax.validation.constraints.NotBlank;
//import java.sql.Date;
//import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class BidList {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer BidListId;
	private String account;
	private String type;
	private Double bidQuantity;
	private Double askQuantity;
	private Double bid;
	private Double ask;
	private String benchmark;
	private Timestamp bidListDate;
	private String commentary;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creationName;
	private Timestamp creationDate;
	private String revisionName;
	private Timestamp revisionDate;
	private String dealName;
	private String dealType;
	private String sourceListId;
	private String side;

	
}
