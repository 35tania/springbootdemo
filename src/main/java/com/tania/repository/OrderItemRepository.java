package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
