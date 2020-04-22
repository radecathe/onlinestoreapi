package com.radecathe.onlinestoreapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@Entity
@Table(name = "order_products")
@ApiModel(description = "OrderProducts object")
public class OrderProduct {
	@JsonIgnore
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "OrderProducts id", hidden = true)
    private Long id;
    
    @NotNull(message = "Expected field: 'quantity'")
    @ApiModelProperty(notes = "OrderProducts quantity")
    private Integer quantity;

    @NotNull(message = "Expected field: 'product_id'")
    @ApiModelProperty(notes = "Product id")
    private Long product_id;
    
    @JsonIgnore
    @OneToOne
    @ApiModelProperty(notes = "Order id", hidden = true)
    private Order order; 
 
	public Order getOrder() {
		return order; 
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getId() {
		return id; 
	}

	public void setId(Long id) { 
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	
}