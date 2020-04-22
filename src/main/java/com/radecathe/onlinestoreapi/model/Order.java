package com.radecathe.onlinestoreapi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@Entity
@Table(name = "\"order\"")
@ApiModel(description = "Order object")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Order id")
    private Long id;
    
    @NotNull(message = "Expected field: 'customer_id'")
    @ApiModelProperty(notes = "Customer id")
    private Long customer_id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @ApiModelProperty(notes = "Order items") 
    private Set<OrderProduct> orderProduct = new HashSet<OrderProduct>();
    
    @CreationTimestamp
    @Column(name="creation_date") 
    @ApiModelProperty(notes = "Order Date", hidden = true)
    private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public Set<OrderProduct> getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(Set<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}