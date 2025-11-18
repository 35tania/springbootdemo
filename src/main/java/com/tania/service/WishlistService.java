package com.tania.service;


import com.tania.exception.WishlistNotFoundException;
import com.tania.model.Product;
import com.tania.model.User;
import com.tania.model.Wishlist;

import java.util.Optional;

public interface WishlistService {

    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}

