package com.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//One store can have many products


//@Setter
//@Getter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Store {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)	
private int storeId;
@Column(name = "name")
private String storeName;
//@Column(length = 30)
private String address;

@OneToMany(mappedBy = "store",cascade = CascadeType.PERSIST)
@JsonIgnoreProperties
List<Product> products;



public Store() {
	super();
	// TODO Auto-generated constructor stub
}

public Store(int storeId, String storeName, String address) {
	super();
	this.storeId = storeId;
	this.storeName = storeName;
	this.address = address;
	//this.products = products;
}

public int getStoreId() {
	return storeId;
}

public void setStoreId(int storeId) {
	this.storeId = storeId;
}

public String getStoreName() {
	return storeName;
}

public void setStoreName(String storeName) {
	this.storeName = storeName;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public List<Product> getProducts() {
	return products;
}

public void setProducts(List<Product> products) {
	this.products = products;
}

@Override
public String toString() {
	return "Store [storeId=" + storeId + ", storeName=" + storeName + ", address=" + address + ", products=" + products
			+ "]";
}


}
