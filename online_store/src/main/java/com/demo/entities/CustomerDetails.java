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

@Entity
public class CustomerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cusId;
	@Column(length = 30)
	private String customerName;
	@Column(length = 50)
	private String customerAddress;
	
	@OneToMany(mappedBy = "customerDetails",cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties
	private List<OrderDetails> orderDetails;
	
	
	
	public CustomerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
//	public CustomerDetails(int cusId, String customerName, String customerAddress, List<OrderDetails> orderDetails) {
//		super();
//		this.cusId = cusId;
//		this.customerName = customerName;
//		this.customerAddress = customerAddress;
//		this.orderDetails = orderDetails;
//	}
	public int getCusId() {
		return cusId;
	}
	public void setCusId(int cusId) {
		this.cusId = cusId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
}
