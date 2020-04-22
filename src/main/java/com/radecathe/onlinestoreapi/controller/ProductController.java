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
import com.radecathe.onlinestoreapi.model.Product;
import com.radecathe.onlinestoreapi.repository.ProductRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@RestController
@RequestMapping("/products")
@Api(value = "Products API", tags = "Products", description = "Operations pertaining to product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository; 
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a list of products", response = Product.class, responseContainer = "List")
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Return a product by id", response = Product.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProduct(
    		@ApiParam(value = "Product id", required = true) @PathVariable Long id) {
    	Product product = getProductById(id);
    	return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Add a product", response = Product.class)
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> saveProduct(
    		@ApiParam(value = "Product object", required = true) @Valid @RequestBody Product product) {
    	product.setId(null);
    	return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED); 
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Update a product by id", response = Product.class)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateProduct(
    		@ApiParam(value = "Product id", required = true) @PathVariable Long id, 
    		@ApiParam(value = "Product object", required = true) @Valid @RequestBody Product product) {
    	getProductById(id);
    	product.setId(id);
    	
    	return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }
    
    @SuppressWarnings("deprecation")
	@ApiOperation(value = "Delete a product by id")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteProduct(
    		@ApiParam(value = "Product id", required = true) @PathVariable Long id) {
    	getProductById(id);
    	productRepository.deleteById(id);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private Product getProductById(Long id) {
    	Optional<Product> product = productRepository.findById(id);
 
    	if (product.isEmpty())
            throw new ResourceNotFoundException("Product not found: " + id); 
    	
    	return product.get();
    }
}