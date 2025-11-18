package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByUserId(Long userId);
}
