package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	/**
	 * Searching User by it's "username".
	 * @param username.
	 * */
	public Optional<User> findByUsername(String username);

}
