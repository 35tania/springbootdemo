package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Cart;
import com.tania.model.CartItem;
import com.tania.model.Product;
import com.tania.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);


}
