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
@Table(name = "customer")
@ApiModel(description = "Customer object")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Customer id")
    private Long id;
    
    @NotEmpty(message = "Expected field: 'first name'")
    @ApiModelProperty(notes = "Customer first name")
    private String firstName;
    
    @NotEmpty(message = "Expected field: 'last name'")
    @ApiModelProperty(notes = "Customer last name")
    private String lastName;
    
    @NotEmpty(message = "Expected field: 'document'")
    @ApiModelProperty(notes = "Customer document")
    private String document;
    
    @NotEmpty(message = "Expected field: 'address'")
    @ApiModelProperty(notes = "Customer address")
    private String address;

    @NotNull(message = "Expected field: 'zipCode'")
    @ApiModelProperty(notes = "Customer zipCode")
    private String zipCode;
    
    @NotNull(message = "Expected field: 'city'")
    @ApiModelProperty(notes = "Customer city")
    private String city;
    
    @NotNull(message = "Expected field: 'state'")
    @ApiModelProperty(notes = "Customer state")
    private String state;
    
    @NotNull(message = "Expected field: 'country'")
    @ApiModelProperty(notes = "Customer country")
    private String country;
    
    @NotNull(message = "Expected field: 'phone'")
    @ApiModelProperty(notes = "Customer phone")
    private String phone;
    
    @NotNull(message = "Expected field: 'email'")
    @ApiModelProperty(notes = "Customer email")
    private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) { 
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}