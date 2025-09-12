package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Get logged user's informations.
	 * */
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException(
				"Utilisateur non trouvé dans la base de données pour l'email: " + username));
	}
	
	/**
	 * Find all users.
	 * @return List of users.
	 * */
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	
	/**
	 * Find user by id
	 * @param User's id
	 * @return User
	 * */
	public Optional<User> findById(Integer id){
		return userRepository.findById(id);
	}
	
	/**
	 * Save User into data base
	 * @param User
	 * @return User
	 * */
	public User save(User user){
		return userRepository.save(user);
	}
	
	/**
	 * Making User and put informations before saving.
	 * @param User
	 * @return User
	 * */
	public User validate(User user){
			
		User newUser = user;
		newUser.setRole("USER");
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		return save(newUser);
	}
	
	/**
	 * Update user's informations.
	 * @param User
	 * @return User
	 * */
	public User update(User user){
			
		User newUser = user;
		Optional<User> currentUserOptional = findById(user.getId());
		
		User currentUserInfos = currentUserOptional.get();
		currentUserInfos.setFullname(newUser.getFullname());
		currentUserInfos.setUsername(newUser.getUsername());
		currentUserInfos.setRole(newUser.getRole());
		currentUserInfos.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		return save(currentUserOptional.get());
	}
	
	/**
	 * Delete user.
	 * @param User.
	 * */
	public void deleteUser(User user){
		userRepository.delete(user);
	}
	
}
