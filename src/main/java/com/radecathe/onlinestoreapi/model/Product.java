package com.radecathe.onlinestoreapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@Entity
@Table(name = "product")
@ApiModel(description = "Product object")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Product id")
    private Long id;
    
    @NotEmpty(message = "Expected field: 'description'")
    @ApiModelProperty(notes = "Product description")
    private String description;

    @NotNull(message = "Expected field: 'price'")
    @ApiModelProperty(notes = "Product price")
    private Double price;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) { 
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}