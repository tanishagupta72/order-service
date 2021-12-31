package com.ibm.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.order.model.InitiateOrderRequestBody;
import com.ibm.order.model.InitiateOrderResponseBody;
import com.ibm.order.model.ReceiveOrderRequestBody;
import com.ibm.order.model.ReceiveOrderResponseBody;
import com.ibm.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OrderServiceController {

	@Autowired
	OrderService orderService;
	
	@PostMapping("/initiateOrder")
	public ResponseEntity<?> initiateOrder(@RequestHeader(value ="Authorization" ,required = true) String requestTokenHeader,@RequestBody InitiateOrderRequestBody request)
	{

		 if(requestTokenHeader != null && !requestTokenHeader.equalsIgnoreCase("") && requestTokenHeader.startsWith("Bearer "))
		 {
			String token = requestTokenHeader.substring(7);
			log.info("Incoming token : "+ token);
			int orderId = orderService.initiateOrder(request.getUserName(), token);
			log.info("Order id : "+ orderId);
			return ResponseEntity.status(HttpStatus.OK).body(new InitiateOrderResponseBody("Success","Order initiated",orderId));
		 }
		 else
		 {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new InitiateOrderResponseBody("Failed","Request Unauthorized",0));
		 }
		
		
	}
	
	@PostMapping("/receiveOrder")
	public ResponseEntity<?> receiveOrder(@RequestBody ReceiveOrderRequestBody request)
	{
		
		ReceiveOrderResponseBody response = orderService.receiveOrder(request.getOrderId(),request.getOrderList());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
