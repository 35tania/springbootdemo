package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.SellerReport;

public interface SellerReportRepository extends JpaRepository<SellerReport,Long> {
    SellerReport findBySellerId(Long sellerId);
}
