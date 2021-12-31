package com.ibm.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitiateOrderResponseBody {
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("OrderId")
	private int orderId;

}
