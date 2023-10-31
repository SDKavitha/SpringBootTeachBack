package com.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Many products belongs to one store


//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//@ToString
@Entity
public class Product {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int productId;
@Column(name = "name",length = 30)
private String productName;
@Column(name = "price")
private double productPrice;
private int stock;

@ManyToOne(cascade = CascadeType.PERSIST)
@JsonIgnoreProperties
private Store store;

//@OneToMany(mappedBy = "product",cascade = CascadeType.PERSIST)
//@JsonIgnoreProperties
//List<OrderDetails> orderDetails;



public Product() {
	super();
	// TODO Auto-generated constructor stub
}

public int getProductId() {
	return productId;
}

public void setProductId(int productId) {
	this.productId = productId;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public double getProductPrice() {
	return productPrice;
}

public void setProductPrice(double productPrice) {
	this.productPrice = productPrice;
}

public int getStock() {
	return stock;
}

public void setStock(int stock) {
	this.stock = stock;
}

public Store getStore() {
	return store;
}

public void setStore(Store store) {
	this.store = store;
}




}
