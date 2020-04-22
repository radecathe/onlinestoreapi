package com.radecathe.onlinestoreapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.radecathe.onlinestoreapi.model.Customer;
import com.radecathe.onlinestoreapi.repository.CustomerRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@RestController
@RequestMapping("/customers")
@Api(value = "Customers API", tags = "Customers", description = "Operations pertaining to customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository; 
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a list of customers", response = Customer.class, responseContainer = "List")
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCustomers() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a customer by id", response = Customer.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCustomer(
    		@ApiParam(value = "Customer id", required = true) @PathVariable Long id) {
    	Customer customer = getCustomerById(id);
    	return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Add a customer", response = Customer.class)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> saveCustomer(
    		@ApiParam(value = "Customer object", required = true) @Valid @RequestBody Customer customer) {
    	customer.setId(null);
    	return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED); 
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Update a customer by id", response = Customer.class)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateCustomer(
    		@ApiParam(value = "Customer id", required = true) @PathVariable Long id,
    		@ApiParam(value = "Customer object", required = true) @Valid @RequestBody Customer customer) {
    	getCustomerById(id);
    	customer.setId(id);
    	
    	return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Delete a customer by id")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteCustomer(
    		@ApiParam(value = "Customer id", required = true) @PathVariable Long id) {
    	getCustomerById(id);
    	customerRepository.deleteById(id);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private Customer getCustomerById(Long id) {
    	Optional<Customer> customer = customerRepository.findById(id);
 
    	if (customer.isEmpty())
            throw new ResourceNotFoundException("Customer not found: " + id); 
    	
    	return customer.get();
    }
}