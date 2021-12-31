package com.ibm.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.order.client.LoginServiceClient;
import com.ibm.order.client.ProductServiceClient;
import com.ibm.order.entity.ItemList;
import com.ibm.order.entity.Order;
import com.ibm.order.exception.InvalidTokenException;
import com.ibm.order.exception.OrderNotFoundException;
import com.ibm.order.exception.PreviousTransactionActiveException;
import com.ibm.order.exception.TransactionExpiredException;
import com.ibm.order.exception.TransactionInactiveException;
import com.ibm.order.model.OrderRequestBody;
import com.ibm.order.model.ProductServiceResponseBody;
import com.ibm.order.model.ReceiveOrderResponseBody;
import com.ibm.order.model.ValidateTokenResponseBody;
import com.ibm.order.repository.ItemListRepository;
import com.ibm.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	ItemListRepository itemRepo;
	
	@Autowired
	ProductServiceClient productClient;
	
	@Autowired
	LoginServiceClient loginClient;
	
	public int initiateOrder(String userName,String token)
	{
		log.info("Entering in initiateOrder");
		ValidateTokenResponseBody validateTokenResponse = loginClient.validateToken(token, userName);
		if(validateTokenResponse.isValid() && validateTokenResponse.getUserId()!=0) {
			if(orderRepo.findByUserIdAndUserToken(validateTokenResponse.getUserId(),token)!= null 
					&& isTransactionActive(orderRepo.findByUserIdAndUserToken(validateTokenResponse.getUserId(),token).getUserId()))
			{
				throw new PreviousTransactionActiveException();
			}
			else
			{
				
				Order order = new Order(validateTokenResponse.getUserId(),token, new Date(),new Date());
				Order savedOrder = orderRepo.save(order);
				return savedOrder.getOrderId();
			}
		}
		else
		{
			throw new InvalidTokenException();
		}
	}
	
	public ReceiveOrderResponseBody receiveOrder(int orderId , List<OrderRequestBody> request)
	{
		log.info("Entering in receiveOrder");
		ReceiveOrderResponseBody response = new ReceiveOrderResponseBody();
		if(isTransactionActive(orderId))
		{
				
			for( OrderRequestBody o : request)
			{
				ProductServiceResponseBody productResponse = productClient.getProductById(o.getProductId());
				if(productResponse != null)
				{
					int availableQuantity = productResponse.getAvailableQuantity();
					if (availableQuantity >= o.getQuantity())
					{
					
					ItemList itemList = new ItemList();
					itemList.setOrderId(orderId);
					itemList.setProductId(o.getProductId());
					itemList.setQuantity(o.getQuantity());
					itemList.setCretTs(new Date());
					itemList.setUpdtTs(new Date());
					itemList.setProductName(productResponse.getProductName());
					double itemTotal = o.getQuantity()*productResponse.getPrice();
					itemList.setItemTotal(itemTotal);
					itemRepo.save(itemList);
					
					
					response.setStatus("SUCCESS");
					response.setDescription("Quantity available : "+o.getQuantity());
					response.setItemTotal(itemTotal);
					}
					else if((availableQuantity < o.getQuantity()) && availableQuantity>0)
					{
						
						ItemList itemList = new ItemList();
						itemList.setOrderId(orderId);
						itemList.setProductId(o.getProductId());
						itemList.setQuantity(availableQuantity);
						itemList.setProductName(productResponse.getProductName());
						double itemTotal = availableQuantity*productResponse.getPrice();
						itemList.setItemTotal(itemTotal);
						itemRepo.save(itemList);
						
						response.setStatus("SUCCESS");
						response.setDescription("Quantity available : "+availableQuantity);
						response.setItemTotal(itemTotal);
						
					}
					else
					{
						response.setStatus("FAILED");
						response.setDescription("Product out of stock");
						response.setItemTotal(0);
						
					}
				}
			}log.info("Exiting from in receiveOrder");
		}
		else
		{
			throw new TransactionExpiredException();
			
		}
	
		return response;
	}
	
	public boolean isTransactionActive(int orderId)
	{
		log.info("Entering in isTransactionActive for orderId :"+orderId);
		boolean isActive = false;
		try {
			Order order = orderRepo.findByOrderId(orderId);
			if(order != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				Date orderTs = order.getCretTs();
				Date orderDt =  sdf.parse(orderTs.toString());
						 

				
				long diff = System.currentTimeMillis() - orderDt.getTime();
				log.info("Diff : "+diff);
				long diffInMin = diff/(1000*60)%60;
				log.info("Diff in min : "+diffInMin);
				if(diffInMin <= 30)
				{
					isActive = true;
				}
				log.info("Exiting from  isTransactionActive");
				return isActive;
			}
			else 
			{
				throw new OrderNotFoundException();
			}
			
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new TransactionInactiveException();
		}
		
	}
}
