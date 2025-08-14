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
	 * Helper function that converts a user's role (e.g., "USER", "ADMIN") into a format that Spring Security can understand and use for authorization.
	 * */
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
	
}
