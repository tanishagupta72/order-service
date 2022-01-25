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
public class ReceiveOrderResponseBody {

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("ProductList")
	private List<ProductServiceResponseBody> productList;
	
	@JsonProperty("Itemtotal")
	private double itemTotal;
}
