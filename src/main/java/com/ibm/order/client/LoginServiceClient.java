package com.ibm.order.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.order.exception.InvalidTokenException;
import com.ibm.order.exception.ProductNotFoundException;
import com.ibm.order.exception.TokenValidationException;
import com.ibm.order.model.ProductServiceResponseBody;
import com.ibm.order.model.ValidateTokenRequestBody;
import com.ibm.order.model.ValidateTokenResponseBody;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceClient {

	public ValidateTokenResponseBody validateToken(String token,String userName) {
		try {
			String url = "http://localhost:8080/validate";
			log.debug("Target URL :"+url);
			ValidateTokenRequestBody request = new ValidateTokenRequestBody(userName,token);
			log.debug("Validate token request  : "+request.toString());
			RestTemplate restTemplate = new RestTemplate();
			ValidateTokenResponseBody response = restTemplate.postForObject(url,request,ValidateTokenResponseBody.class);
			log.debug("Validate token response  : "+response.toString());
			return response;
			}
			catch(Exception e)
			{
				throw new TokenValidationException();
			}
	}
}
