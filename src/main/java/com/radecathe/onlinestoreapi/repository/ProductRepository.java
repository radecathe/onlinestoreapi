package com.radecathe.onlinestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radecathe.onlinestoreapi.model.Product;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

public interface ProductRepository extends JpaRepository<Product, Long> {}