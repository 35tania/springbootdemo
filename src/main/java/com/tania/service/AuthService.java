package com.tania.service;

import com.tania.exception.SellerException;
import com.tania.exception.UserException;
import com.tania.request.LoginRequest;
import com.tania.request.ResetPasswordRequest;
import com.tania.request.SignupRequest;
import com.tania.response.ApiResponse;
import com.tania.response.AuthResponse;

import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signin(LoginRequest req) throws SellerException;

}
