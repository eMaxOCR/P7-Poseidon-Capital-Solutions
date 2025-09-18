package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	 
    @Id 
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{8,}$", message = "Le mot de passe doit contenir au minimum un chiffre, une minuscule, une majuscule et un caractère spécial.")
    private String password;
    
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
     
    //@NotBlank(message = "Role is mandatory") 
    private String role;
    
//    @OneToMany(
//    		mappedBy = "user",			//User "user" from "Curve Point"
//    		cascade = CascadeType.ALL, 	//If user removed, remove curve points.
//    		fetch = FetchType.LAZY 		//Load only when asked.
//    		)
//    private List<CurvePoint> curvePoints;
        
}
