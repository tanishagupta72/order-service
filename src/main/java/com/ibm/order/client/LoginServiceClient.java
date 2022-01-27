package com.ibm.order.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import com.ibm.order.exception.TokenValidationException;

import com.ibm.order.model.ValidateTokenRequestBody;
import com.ibm.order.model.ValidateTokenResponseBody;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceClient {

	@Value("${login.url}")
	String loginPrefix ;
	
	public ValidateTokenResponseBody validateToken(String token,String userName) {
		try {
			
			String url = loginPrefix + "/validate";
			log.info("Target URL :"+url);
			ValidateTokenRequestBody request = new ValidateTokenRequestBody(userName,token);
			log.info("Validate token request  : Token : "+request.getToken()+"Username : "+request.getUserName());
			RestTemplate restTemplate = new RestTemplate();
			ValidateTokenResponseBody response = restTemplate.postForObject(url,request,ValidateTokenResponseBody.class);
			log.info("Validate token response userid : "+response.getUserId()+" isValid : "+response.isValid());
			return response;
			}
			catch(Exception e)
			{
				throw new TokenValidationException();
			}
	}
}
