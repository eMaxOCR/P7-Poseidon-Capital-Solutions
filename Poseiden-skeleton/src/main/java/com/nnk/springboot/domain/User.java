package com.nnk.springboot.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
	
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @NotBlank(message = "Password is mandatory")
    private String password;
    
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    
    @NotBlank(message = "Role is mandatory")
    private String role;
    
    @OneToMany(
    		mappedBy = "user",			//User "user" from "Curve Point"
    		cascade = CascadeType.ALL, 	//If user removed, remove curve points.
    		fetch = FetchType.LAZY 		//Load only when asked.
    		)
    private List<CurvePoint> curvePoints;
        
}
