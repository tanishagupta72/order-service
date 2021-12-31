package com.ibm.order.client;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.order.exception.ProductNotFoundException;
import com.ibm.order.model.ProductServiceResponseBody;

@Service
public class ProductServiceClient {

	public ProductServiceResponseBody getProductById(int productId) {
	try {
		String url = "http://localhost:8100/productById/" + productId;
		System.out.println("Target URL :"+url);
		RestTemplate restTemplate = new RestTemplate();
		ProductServiceResponseBody response = restTemplate.getForObject(url,ProductServiceResponseBody.class);
		return response;
		}
		catch(Exception e)
		{
			throw new ProductNotFoundException();
		}
}
	
}
