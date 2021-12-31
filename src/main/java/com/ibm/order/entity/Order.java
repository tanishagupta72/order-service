package com.ibm.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class Order {

	@Id
	@GeneratedValue
	private int orderId;
	
	@Column
	private int userId;
	
	@Column
	private String userToken;
	
	@Column
	private Date cretTs;
	
	@Column
	private Date updtTs;
	
	
	
	/**
	 * 
	 */
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userId
	 * @param userToken
	 * @param cretTs
	 * @param updtTs
	 */
	public Order(int userId, String userToken, Date cretTs, Date updtTs) {
		super();
		this.userId = userId;
		this.userToken = userToken;
		this.cretTs = cretTs;
		this.updtTs = updtTs;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
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

	
	
	
}
