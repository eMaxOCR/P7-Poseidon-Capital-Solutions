package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 			//Spring detect this class has configuration class.
@EnableWebSecurity 		//Enable web security session based.
@EnableMethodSecurity   //Enable ROLE access.
public class SpringSecurityAuthApplication {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Bean
	/**
	 * Configures the security filter chain for the application.
	 *
	 * This method defines the security behavior, including which URLs are public,
	 * the custom login page, and the redirection paths after authentication or logout.
	 *
	 * @param http the HttpSecurity object to configure.
	 * @return the configured SecurityFilterChain.
	 * @throws Exception if an error occurs during configuration.
	 **/
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(auth -> {
			//auth.requestMatchers("/admin").hasRole("ADMIN"); 	//Define admin and his role
			//auth.requestMatchers("/user").hasRole("USER");		//Define user and his role
			auth.requestMatchers("/app/signup", "/app/login", "/401", "/403", "/404", "/500", "/css/**", "/js/**", "/images/**", "/error").permitAll();
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
	 * Creates and configures a bean for password encoding.
	 *
	 * This method uses the strong BCrypt hashing algorithm to securely
	 * encode passwords before they are stored in the database.
	 *
	 * @return an instance of BCryptPasswordEncoder.
	 **/
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	/**
	 * Creates and configures the Authentication Manager.
	 *
	 * This bean is the main component responsible for handling the authentication process.
	 * It uses the provided user details service and password encoder to validate
	 * a user's credentials during login.
	 *
	 * @param http the HttpSecurity object used to get the shared AuthenticationManagerBuilder.
	 * @param bCryptPasswordEncoder the password encoder service used to check the password.
	 * @return a configured AuthenticationManager instance.
	 * @throws Exception if an error occurs while creating the AuthenticationManager.
	 **/
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    return authenticationManagerBuilder.build();
	}
}
