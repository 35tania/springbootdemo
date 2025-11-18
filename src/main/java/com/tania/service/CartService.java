package com.tania.service;

import com.tania.exception.ProductException;
import com.tania.model.Cart;
import com.tania.model.CartItem;
import com.tania.model.Product;
import com.tania.model.User;
import com.tania.request.AddItemRequest;

public interface CartService {
	
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity) throws ProductException;
	
	public Cart findUserCart(User user);

}
