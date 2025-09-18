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
	 * Gets the information of the currently logged-in user.
	 *
	 * @return the User object of the logged-in user.
	 */
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException(
				"Utilisateur non trouvé dans la base de données pour l'email: " + username));
	}
	
	/**
	 * Finds all users in the database.
	 *
	 * @return a list of all User objects.
	 */
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	
	/**
	 * Finds a user by their ID.
	 *
	 * @param id the user's ID.
	 * @return an Optional object containing the found user, or an empty Optional if not found.
	 */
	public Optional<User> findById(Integer id){
		return userRepository.findById(id);
	}
	
	/**
	 * Saves a user to the database.
	 *
	 * @param user the User object to be saved.
	 * @return the saved User object.
	 */
	public User save(User user){
		return userRepository.save(user);
	}
	
	/**
	 * Prepares and validates a new user before saving.
	 * The method sets the default role to "USER" and encodes the password.
	 *
	 * @param user the User object to be validated and saved.
	 * @return the validated and saved User object.
	 */
	public User validate(User user){
			
		User newUser = user;
		newUser.setRole("USER");
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		return save(newUser);
	}
	
	/**
	 * Updates the information of an existing user.
	 *
	 * @param user the User object with the new information.
	 * @return the updated and saved User object.
	 */
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
	 * Deletes a user from the database.
	 *
	 * @param user the User object to be deleted.
	 */
	public void deleteUser(User user){
		userRepository.delete(user);
	}
	
}
