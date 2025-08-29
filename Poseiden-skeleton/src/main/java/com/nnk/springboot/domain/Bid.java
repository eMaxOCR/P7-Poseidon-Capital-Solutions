package com.nnk.springboot.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class Bid {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer BidListId;
	
	@NotBlank(message="Account is mandatory")
	private String account;
	
	@NotBlank(message="Type is mandatory")
	private String type;
	
	private Double bidQuantity;
	private Double askQuantity;
	private Double bid;
	private Double ask;
	private String benchmark;
	private LocalDateTime bidListDate;
	private String commentary;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creationName;
	private LocalDateTime creationDate;
	private String revisionName;
	private LocalDateTime revisionDate;
	private String dealName;
	private String dealType;
	private String sourceListId;
	private String side;

	
}
