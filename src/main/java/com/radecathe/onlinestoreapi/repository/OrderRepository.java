package com.radecathe.onlinestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radecathe.onlinestoreapi.model.Order;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

public interface OrderRepository extends JpaRepository<Order, Long> {}