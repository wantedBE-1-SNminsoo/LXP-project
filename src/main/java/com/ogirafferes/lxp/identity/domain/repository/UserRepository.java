package com.ogirafferes.lxp.identity.domain.repository;

import com.ogirafferes.lxp.identity.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsernameAndDeletedFlagFalse(String username);
	boolean existsByUsernameAndDeletedFlagFalse(String username);
	default Optional<User> findByUsername(String username) {
		return findByUsernameAndDeletedFlagFalse(username);
	}
	
	default boolean existsByUsername(String username) {
		return existsByUsernameAndDeletedFlagFalse(username);
	}
}

