package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Coupon findByCode(String couponCode);
}
