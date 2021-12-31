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
public class ProductServiceRequestBody {

	@JsonProperty("ProductId")
	private int productId;
	
	@JsonProperty("ProductName")
	private String productName;
	
	
}
