package com.tania.service.impl;

import com.tania.config.JwtProvider;
import com.tania.domain.USER_ROLE;
import com.tania.exception.SellerException;
import com.tania.exception.UserException;
import com.tania.model.Address;
import com.tania.model.Cart;
import com.tania.model.PasswordResetToken;
import com.tania.model.User;
import com.tania.model.VerificationCode;
import com.tania.repository.CartRepository;
import com.tania.repository.UserRepository;
import com.tania.repository.VerificationCodeRepository;
import com.tania.request.AddressRequest;
import com.tania.request.LoginRequest;
import com.tania.request.ResetPasswordRequest;
import com.tania.request.SignupRequest;
import com.tania.response.ApiResponse;
import com.tania.response.AuthResponse;
import com.tania.service.AuthService;
import com.tania.service.EmailService;
import com.tania.service.UserService;
import com.tania.utils.OtpUtils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final JwtProvider jwtProvider;
    private final CustomeUserServiceImplementation customUserDetails;
    private final CartRepository cartRepository;


//    @Override
//    public void sentLoginOtp(String email) throws UserException, MessagingException {
//        String SIGNING_PREFIX = "signing_";
//        if (email.startsWith(SIGNING_PREFIX)) {
//            email = email.substring(SIGNING_PREFIX.length());
//            userService.findUserByEmail(email);
//        }
//        VerificationCode isExist = verificationCodeRepository
//                .findByEmail(email);
//        if (isExist != null) {
//            verificationCodeRepository.delete(isExist);
//        }
//        String otp = OtpUtils.generateOTP();
//        VerificationCode verificationCode = new VerificationCode();
//        verificationCode.setOtp(otp);
//        verificationCode.setEmail(email);
//        verificationCodeRepository.save(verificationCode);
//        String subject = "Firgomart Login/Signup Otp";
////        String text = "your login otp is - ";
//        String text =
//                "<div style='font-family:Arial,sans-serif; padding:15px;'>" +
//                "<h2 style='color:#2E86C1;'>Your OTP Code</h2>" +
//                "<p>Dear User,</p>" +
//                "<p>We received a request to verify your email for login/signup at <b>Firgomart</b>.</p>" +
//                "<p>Your One-Time Password (OTP) is:</p>" +
//                "<h1 style='background:#2E86C1; color:white; padding:10px; display:inline-block; border-radius:8px;'>" + otp + "</h1>" +
//                "<p>This OTP is valid for <b>10 minutes</b>. Please do not share it with anyone.</p>" +
//                "<p>If you did not request this, you can safely ignore this message.</p>" +
//                "<br>" +
//                "<p>Best Regards,<br><b>Firgomart Team</b></p>" +
//                "<hr>" +
//                "<small style='color:gray;'>This is an automated message. Please do not reply.</small>" +
//                "</div>";
//        emailService.sendVerificationOtpEmail(email, otp, subject, text);
//    }
    
    
//    @Override
//    public void sentLoginOtp(String input) throws UserException, MessagingException {
//
//        String SIGNING_PREFIX = "signing_";
//
//        // Check if signing prefix exists
//        if (input.startsWith(SIGNING_PREFIX)) {
//            input = input.substring(SIGNING_PREFIX.length());
//        }
//
//        String email = null;
//        String mobile = null;
//
//        // Decide if it's email or mobile
//        if (input.contains("@")) {
//            email = input;
//        } else {
//            mobile = input;
//        }
//
//        // Delete old OTP if exists
//        VerificationCode isExist;
//        if (email != null) {
//            isExist = verificationCodeRepository.findByEmail(email);
//        } else {
//            isExist = verificationCodeRepository.findByMobile(mobile);
//        }
//
//        if (isExist != null) verificationCodeRepository.delete(isExist);
//
//        // Generate OTP
//        String otp = OtpUtils.generateOTP();
//
//        // Save new OTP
//        VerificationCode verificationCode = new VerificationCode();
//        verificationCode.setOtp(otp);
//        verificationCode.setEmail(email);
//        verificationCode.setMobile(mobile);
//        verificationCodeRepository.save(verificationCode);
//
//        // Send via EMAIL
//        if (email != null) {
//            String subject = "Firgomart Login/Signup Otp";
//
//            String text =
//                "<div style='font-family:Arial,sans-serif; padding:15px;'>" +
//                "<h2>Your OTP Code</h2>" +
//                "<p>Your One-Time Password (OTP) is:</p>" +
//                "<h1>" + otp + "</h1>" +
//                "<p>This OTP is valid for 10 minutes.</p>" +
//                "</div>";
//
//            emailService.sendVerificationOtpEmail(email, otp, subject, text);
//            return;
//        }
//
//        // Send via SMS (TEXTBEE)
//        if (mobile != null) {
//            smsService.sendOtpSms(mobile, otp);
//            return;
//        }
//
//        throw new UserException("Invalid input! Provide email or mobile.");
//    }
    
    @Override
    public void sentLoginOtp(String input) throws UserException, MessagingException {

        String SIGNING_PREFIX = "signing_";

        // Remove signing_ prefix if present
        if (input.startsWith(SIGNING_PREFIX)) {
            input = input.substring(SIGNING_PREFIX.length());
        }

        String email = null;
        String mobile = null;

        // Identify email or mobile
        if (input.contains("@")) {
            email = input;
        } else {
            mobile = input;
        }

        // Delete old OTP if exists
        VerificationCode existing;
        if (email != null) {
            existing = verificationCodeRepository.findByEmail(email);
        } else {
            existing = verificationCodeRepository.findByMobile(mobile);
        }

        if (existing != null) {
            verificationCodeRepository.delete(existing);
        }

        // Generate OTP
        String otp = OtpUtils.generateOTP();

        // Save OTP
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCode.setMobile(mobile);
        verificationCodeRepository.save(verificationCode);

        // ============================
        //          EMAIL OTP
        // ============================
        if (email != null) {

            String subject = "Firgomart Login/Signup OTP";

            String text =
                    "<!DOCTYPE html>" +
                    "<html lang='en'>" +
                    "<head>" +
                    "    <meta charset='UTF-8' />" +
                    "    <meta name='viewport' content='width=device-width, initial-scale=1.0' />" +
                    "    <title>Firgomart OTP</title>" +
                    "    <style>" +
                    "        body { background:#f4f7fa; margin:0; padding:0; font-family:Arial, sans-serif; }" +
                    "        .container { max-width:550px; margin:40px auto; background:#fff; padding:30px;" +
                    "            border-radius:12px; box-shadow:0 4px 14px rgba(0,0,0,0.1); }" +
                    "        .header { text-align:center; padding-bottom:15px; border-bottom:1px solid #eee; }" +
                    "        .header h1 { margin:0; color:#2E86C1; font-size:24px; font-weight:700; }" +
                    "        .content { padding:20px 0; font-size:16px; color:#444; line-height:1.7; }" +
                    "        .otp-box { text-align:center; margin:30px 0; }" +
                    "        .otp { background:#2E86C1; color:#fff; display:inline-block; padding:15px 40px;" +
                    "              font-size:32px; border-radius:10px; letter-spacing:5px; font-weight:bold; }" +
                    "        .footer { text-align:center; padding-top:20px; border-top:1px solid #eee; font-size:14px; color:#888; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "    <div class='header'><h1>Firgomart Verification Code</h1></div>" +
                    "    <div class='content'>" +
                    "        <p>Hello,</p>" +
                    "        <p>Use the following One-Time Password (OTP) to complete your login or signup on <b>Firgomart</b>.</p>" +
                    "        <p>Your OTP is:</p>" +
                    "    </div>" +
                    "    <div class='otp-box'><div class='otp'>" + otp + "</div></div>" +
                    "    <div class='content'>" +
                    "        <p>This OTP is valid for <b>10 minutes</b>. Please do not share it with anyone.</p>" +
                    "        <p>If you did not request this, simply ignore this message.</p>" +
                    "    </div>" +
                    "    <div class='footer'>" +
                    "        <p>Best Regards,<br><b>Firgomart Team</b></p>" +
                    "        <p>This is an automated email. Do not reply.</p>" +
                    "    </div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            emailService.sendVerificationOtpEmail(email, otp, subject, text);
            return;
        }

        // ============================
        //          SMS OTP
        // ============================
        if (mobile != null) {
            smsService.sendOtpSms(mobile, otp);
            return;
        }

        throw new UserException("Invalid input! Provide email or mobile.");
    }



    @Override
    public String createUser(SignupRequest req) throws SellerException {
        String email = req.getEmail();
        String fullName = req.getFullName();
        String otp = req.getOtp();
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new SellerException("Wrong otp...");
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new SellerException("Email already registered");
        }
        if (user == null) {
            User createdUser = new User();
            createdUser.setEmail(email);
            createdUser.setFullName(fullName);
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile(req.getMobile());
            createdUser.setPassword(passwordEncoder.encode(otp));
            createdUser.setCountry(req.getCountry());
            createdUser.setPass(req.getPass());
            createdUser.setConfirmPassword(req.getConfirmPassword());
            createdUser.setGender(req.getGender());
            createdUser.setDateOfBirth(req.getDateOfBirth());
            
            
            Set<Address> addressSet = new HashSet<>();

            for (AddressRequest ar : req.getAddresses()) {
                Address address = new Address();
                address.setName(ar.getName());
                address.setLocality(ar.getLocality());
                address.setAddress(ar.getAddress());
                address.setCity(ar.getCity());
                address.setState(ar.getState());
                address.setPinCode(ar.getPinCode());
                address.setMobile(ar.getMobile());
                address.setUser(createdUser);   // Important
                addressSet.add(address);
            }

            createdUser.setAddresses(addressSet);
            
            
            System.out.println(createdUser);
            user = userRepository.save(createdUser);
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(
                USER_ROLE.ROLE_CUSTOMER.toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signin(LoginRequest req) throws SellerException {
        String username = req.getEmail();
        String otp = req.getOtp();
        System.out.println(username + " ----- " + otp);
        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;

    }



    private Authentication authenticate(String username, String otp) throws SellerException {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        System.out.println("sign in userDetails - " + userDetails);
        if (userDetails == null) {
            System.out.println("sign in userDetails - null ");
            throw new BadCredentialsException("Invalid username or password");
        }
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new SellerException("Wrong otp...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
