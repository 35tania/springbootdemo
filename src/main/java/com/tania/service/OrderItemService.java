package com.tania.service;


import com.tania.exception.OrderException;
import com.tania.model.OrderItem;
import com.tania.model.Product;

public interface OrderItemService {

	OrderItem getOrderItemById(Long id) throws Exception;
	


}
