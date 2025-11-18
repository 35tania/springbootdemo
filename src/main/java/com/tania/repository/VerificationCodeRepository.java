package com.tania.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByEmail(String email);
    VerificationCode findByOtp(String otp);
	VerificationCode findByMobile(String mobile);
}
