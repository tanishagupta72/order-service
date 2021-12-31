package com.ibm.order.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ibm.order.exception.InvalidTokenException;
import com.ibm.order.exception.OrderNotFoundException;
import com.ibm.order.exception.ProductNotFoundException;
import com.ibm.order.exception.TokenValidationException;
import com.ibm.order.exception.TransactionExpiredException;
import com.ibm.order.model.ErrorResponse;

@RestControllerAdvice
public class OrderServiceAdvice {

	

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error", "Order not found", new Date()));
	}
	
	@ExceptionHandler(TransactionExpiredException.class)
	public ResponseEntity<ErrorResponse> handleTransactionExpiredException(TransactionExpiredException e)
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Error", "Transaction expired,please login again", new Date()));
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error", "Product not found", new Date()));
	}
	

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e)
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Error", "Invalid Token", new Date()));
	}
	
	@ExceptionHandler(TokenValidationException.class)
	public ResponseEntity<ErrorResponse> handleTokenValidationException(TokenValidationException e)
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Error", "Token could not be validated", new Date()));
	}
}
