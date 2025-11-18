package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.HomeCategory;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory,Long> {
}
