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
import com.ibm.order.exception.TransactionExpiredException;
import com.ibm.order.model.OrderRequestBody;
import com.ibm.order.model.ProductServiceResponseBody;
import com.ibm.order.model.ReceiveOrderResponseBody;
import com.ibm.order.model.ValidateTokenResponseBody;
import com.ibm.order.repository.ItemListRepository;
import com.ibm.order.repository.OrderRepository;

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
		ValidateTokenResponseBody validateTokenResponse = loginClient.validateToken(token, userName);
		if(validateTokenResponse.isValid() && validateTokenResponse.getUserId()!=0) {
			Order order = new Order(validateTokenResponse.getUserId(),token, new Date(),new Date());
			return order.getOrderId();
		}
		else
		{
			throw new InvalidTokenException();
		}
	}
	
	public ReceiveOrderResponseBody receiveOrder(int orderId , List<OrderRequestBody> request)
	{
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
			}
		}
		else
		{
			throw new TransactionExpiredException();
			
		}
	
		return response;
	}
	
	public boolean isTransactionActive(int orderId)
	{
		boolean isActive = false;
		try {
			Order order = orderRepo.findByOrderId(orderId);
			if(order != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				Date orderTs = order.getCretTs();
				Date currentTs = new Date();
				Date orderDt = sdf.parse(orderTs.toString());
				Date currentDt = sdf.parse(currentTs.toString());
				long diff = currentDt.getTime() - orderDt.getTime();
				long diffInMin = diff/(1000*60)%60;
				if(diffInMin <= 30)
				{
					isActive = true;
				}
				return isActive;
			}
			else 
			{
				throw new OrderNotFoundException();
			}
		}
		catch(Exception e)
		{
			throw new OrderNotFoundException();
		}
		
	}
}
