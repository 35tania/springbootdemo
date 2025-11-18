package com.tania.controller;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.tania.config.JwtProvider;
import com.tania.domain.USER_ROLE;
import com.tania.exception.SellerException;
import com.tania.exception.UserException;
import com.tania.model.*;
import com.tania.repository.UserRepository;
import com.tania.repository.VerificationCodeRepository;
import com.tania.request.LoginRequest;
import com.tania.request.ResetPasswordRequest;
import com.tania.request.SignupRequest;
import com.tania.response.ApiResponse;
import com.tania.response.AuthResponse;
import com.tania.service.AuthService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


//    @PostMapping("/sent/signup-otp")
//    public ResponseEntity<ApiResponse> sentLoginOtp(
//            @RequestBody VerificationCode req) throws MessagingException, UserException {
//        authService.sentLoginOtp(req.getEmail());
//        ApiResponse res = new ApiResponse();
//        res.setMessage("Otp sent successfully to email");
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }
    
    @PostMapping("/sent/signup-otp")
    public ResponseEntity<ApiResponse> sentLoginOtp(
            @RequestBody VerificationCode req) throws MessagingException, UserException {

        String input;

        // Prefer email if present, otherwise mobile
        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            input = req.getEmail();
        } else if (req.getMobile() != null && !req.getMobile().isBlank()) {
            input = req.getMobile();
        } else {
            throw new UserException("Provide email or mobile!");
        }

        authService.sentLoginOtp(input);

        ApiResponse res = new ApiResponse();
        res.setMessage("OTP sent successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(
            @Valid
            @RequestBody SignupRequest req)
            throws SellerException {

        String token = authService.createUser(req);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) throws SellerException {

        AuthResponse authResponse = authService.signin(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    
//    @PostMapping("/firebase-login")
//    public ResponseEntity<?> firebaseLogin(@RequestBody Map<String, String> body) throws Exception {
//
//        String idToken = body.get("firebaseToken");
//
//        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);
//
//        String phone = decoded.getPhoneNumber();
//
//        User user = userRepository.findByMobile(phone);
//        if (user == null) {
//            user = new User();
//            user.setMobile(phone);
//            userRepository.save(user);
//        }
//
//        String jwt = jwtProvider.generateToken(phone);
//
//        return ResponseEntity.ok(Map.of("jwt", jwt));
//    }





}
