package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Deal;

public interface DealRepository extends JpaRepository<Deal,Long> {

}
