package com.nnk.springboot.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException(
				"Utilisateur non trouvé dans la base de données pour l'email: " + username));

	}
	
	/**
	 * Save User into data base
	 * @param Bid
	 * @return Bid
	 * */
	public User save(User user){
		return userRepository.save(user);
	}
	
	/**
	 * Making bid and put informations before saving.
	 * @param Bid
	 * @return Bid
	 * */
	public User validate(User user){
		
		User newUser = user;
		newUser.setRole("USER");
		
		return save(newUser);
	}
	
}
