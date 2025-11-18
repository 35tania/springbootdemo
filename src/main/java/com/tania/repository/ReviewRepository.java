package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Product;
import com.tania.model.Review;
import com.tania.model.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findReviewsByUserId(Long userId);
    List<Review> findReviewsByProductId(Long productId);
}
