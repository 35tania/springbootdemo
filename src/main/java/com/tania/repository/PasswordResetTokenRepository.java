package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
