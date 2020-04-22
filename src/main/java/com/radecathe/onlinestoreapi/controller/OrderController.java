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
import com.radecathe.onlinestoreapi.model.Order;
import com.radecathe.onlinestoreapi.model.OrderProduct;
import com.radecathe.onlinestoreapi.repository.OrderProductRepository;
import com.radecathe.onlinestoreapi.repository.OrderRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@RestController
@RequestMapping("/orders")
@Api(value = "Orders API", tags = "Orders", description = "Operations pertaining to order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository; 
    
    @Autowired
    private OrderProductRepository orderProductRepository; 
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a list of orders", response = Order.class, responseContainer = "List")
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getOrders() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a order by id", response = Order.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getOrder(
    		@ApiParam(value = "Order id", required = true) @PathVariable Long id) {
    	Order order = getOrderById(id);
    	return new ResponseEntity<>(order, HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Add a order", response = Order.class)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> saveOrder(
    		@ApiParam(value = "Order object", required = true) @Valid @RequestBody Order order) {
    	order.setId(null);
    	orderRepository.save(order); 
    	
    	for (OrderProduct orderProduct : order.getOrderProduct()) {
    		orderProduct.setId(null);
    		orderProduct.setOrder(order);
			orderProductRepository.save(orderProduct);
		}
    	
    	return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Update a order by id", response = Order.class)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateOrder(
    		@ApiParam(value = "Order id", required = true) @PathVariable Long id,
    		@ApiParam(value = "Order object", required = true) @Valid @RequestBody Order order) {
    	getOrderById(id);
    	order.setId(id);
    	
    	return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Delete a order by id")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteOrder(
    		@ApiParam(value = "Order id", required = true) @PathVariable Long id) {
    	getOrderById(id);
    	orderRepository.deleteById(id);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private Order getOrderById(Long id) {
    	Optional<Order> order = orderRepository.findById(id);
 
    	if (order.isEmpty())
            throw new ResourceNotFoundException("Order not found: " + id); 
    	
    	return order.get();
    }
}