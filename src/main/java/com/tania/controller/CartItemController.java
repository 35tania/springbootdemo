package com.tania.controller;

import com.tania.exception.CartItemException;
import com.tania.exception.UserException;
import com.tania.model.CartItem;
import com.tania.model.User;
import com.tania.response.ApiResponse;
import com.tania.service.CartItemService;
import com.tania.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

	private CartItemService cartItemService;
	private UserService userService;
	
	public CartItemController(CartItemService cartItemService, UserService userService) {
		this.cartItemService=cartItemService;
		this.userService=userService;
	}
	

}
