package com.ibm.order.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveOrderRequestBody {
	@JsonProperty("OrderId")
	private int orderId;
	
	@JsonProperty("Order")
	private List<OrderRequestBody> orderList;
	
	
}
