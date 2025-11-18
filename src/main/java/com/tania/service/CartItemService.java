package com.tania.service;

import com.tania.exception.CartItemException;
import com.tania.exception.UserException;
import com.tania.model.Cart;
import com.tania.model.CartItem;
import com.tania.model.Product;


public interface CartItemService {
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
