package com.ibm.order.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;




public class ErrorResponse {

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("Timestamp")
	private Date timestamp;
	
	private ErrorResponse()
	{
		
	}

	public ErrorResponse(String code, String description, Date timestamp) {
		super();
		this.status = code;
		this.description = description;
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
		
}
