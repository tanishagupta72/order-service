package com.ibm.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;
	
	@Column
	private int productId;

	@Column
	private int orderId;
	
	@Column
	private String productName;
	
	@Column
	private int quantity;
	
	@Column 
	private double itemTotal;
	
	@Column
	private Date cretTs;
	
	@Column
	private Date updtTs;
	
	
	
	/**
	 * 
	 */
	public ItemList() {
		super();
		
	}



	public int getItemId() {
		return itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}



	public int getProductId() {
		return productId;
	}



	public void setProductId(int productId) {
		this.productId = productId;
	}



	public int getOrderId() {
		return orderId;
	}



	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public double getItemTotal() {
		return itemTotal;
	}



	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
	}



	public Date getCretTs() {
		return cretTs;
	}



	public void setCretTs(Date cretTs) {
		this.cretTs = cretTs;
	}



	public Date getUpdtTs() {
		return updtTs;
	}



	public void setUpdtTs(Date updtTs) {
		this.updtTs = updtTs;
	}



	public ItemList(int productId, int orderId, String productName, int quantity, double itemTotal, Date cretTs,
			Date updtTs) {
		super();
		this.productId = productId;
		this.orderId = orderId;
		this.productName = productName;
		this.quantity = quantity;
		this.itemTotal = itemTotal;
		this.cretTs = cretTs;
		this.updtTs = updtTs;
	}

	
	
}
