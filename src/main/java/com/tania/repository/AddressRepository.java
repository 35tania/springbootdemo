package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
