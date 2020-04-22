package com.radecathe.onlinestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radecathe.onlinestoreapi.model.User;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}