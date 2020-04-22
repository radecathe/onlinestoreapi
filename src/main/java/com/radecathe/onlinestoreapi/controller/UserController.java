package com.radecathe.onlinestoreapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radecathe.onlinestoreapi.exception.ResourceNotFoundException;
import com.radecathe.onlinestoreapi.model.User;
import com.radecathe.onlinestoreapi.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@RestController
@RequestMapping("/users")
@Api(value = "Users API", tags = "Users", description = "Operations pertaining to user")
public class UserController {
    @Autowired
    private UserRepository userRepository; 
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a list of users", response = User.class, responseContainer = "List")
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a user by id", response = User.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(
    		@ApiParam(value = "User id", required = true) @PathVariable Long id) {
    	User user = getUserById(id);
    	return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Add a user", response = User.class)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> saveUser(
    		@ApiParam(value = "User object", required = true) @Valid @RequestBody User user) {
    	user.setId(null);
    	user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    	
    	return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED); 
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Update a user by id", response = User.class)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateUser(
    		@ApiParam(value = "User id", required = true) @PathVariable Long id,
    		@ApiParam(value = "User object", required = true) @Valid @RequestBody User user) {
    	getUserById(id);
    	user.setId(id);
    	user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    	
    	return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Delete a user by id")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteUser(
    		@ApiParam(value = "User id", required = true) @PathVariable Long id) {
    	getUserById(id);
    	userRepository.deleteById(id);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private User getUserById(Long id) {
    	Optional<User> user = userRepository.findById(id);
 
    	if (user.isEmpty())
            throw new ResourceNotFoundException("User not found: " + id); 
    	
    	return user.get();
    }
}