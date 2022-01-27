package com.ibm.order.client;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.order.exception.ProductNotFoundException;
import com.ibm.order.model.ProductServiceResponseBody;

@Service
public class ProductServiceClient {

	@Value("${product.url}")
	String productPrefix;
	public ProductServiceResponseBody getProductById(int productId) {
	try {
		String url = productPrefix + "/productById/" + productId;
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
