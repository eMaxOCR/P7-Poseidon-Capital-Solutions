package com.nnk.springboot.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	/**
	 * Load user details for Spring Security.
	 *
	 * This class acts as a bridge between the application's database and Spring's authentication system.
	 * It allows Spring Security to load a user by their username during the login process.
	 * 
	 * @param username of the user attempting to log in.
	 * @return the user's details (username, password, roles) in a UserDetails object.
	 * @throws UsernameNotFoundException if the user is not found in the database.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		com.nnk.springboot.domain.User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("L'utilisateur" + username + " n'a pas été trouvé."));	//Find user from DB
		return new User(
				user.getUsername(),
				user.getPassword(),
				getGrantedAuthorities(user.getRole())
		);
	}
	
	/**
	 * Converts a user's role (e.g., "ADMIN") into a format that Spring Security understands.
	 *
	 * Spring Security requires roles to be prefixed with "ROLE_". This helper method
	 * adds the necessary prefix to ensure security works correctly.
	 *
	 * @param role the string representing the user's role (e.g., "USER" or "ADMIN").
	 * @return a list of granted authorities that includes the user's role formatted for Spring Security.
	 */
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
	
}
