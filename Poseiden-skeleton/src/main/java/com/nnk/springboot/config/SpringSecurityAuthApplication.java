package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 			//Spring detect this class has configuration class.
@EnableWebSecurity 		//Enable web security.
public class SpringSecurityAuthApplication {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Bean
	/**
	 * Configuration bean that defines the entire security behavior of your application.
	 * It's like a director
	 * */ 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/admin").hasRole("ADMIN"); 	//Define admin and his role
			auth.requestMatchers("/user").hasRole("USER");		//Define user and his role
			auth.requestMatchers("/app/signup", "/app/login", "/403", "/404", "/css/**", "/js/**", "/images/**", "/error").permitAll();
			auth.anyRequest().authenticated(); 					//for http"s".
		}).formLogin(form -> form
				    .loginPage("/app/login")						//Define custom web page connexion
					.permitAll()								//All can see this page.
					.defaultSuccessUrl("/curvepoint/list", true)		//Go to main web page when logged.
					.failureUrl("/login?error=true")			//Go to error web page when error detected. 
				)
				.exceptionHandling(exceptions -> exceptions
			            .accessDeniedPage("/403") 				// Forbidden access
			        )
				.logout(logout -> logout
			            .permitAll()
			            .logoutSuccessUrl("/app/login") 			// Redirige vers la page de connexion avec un paramètre de déconnexion après la déconnexion
			        )
				.build(); 										//Create login form page.
	}
	
	@Bean
	/**
	 * Encrypt password
	 * */
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	/**
	 * Manage authentication sources
	 * */
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    return authenticationManagerBuilder.build();
	}
}
