package com.tania.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    private String fullName;
    private String email;
    private String otp;
    private String mobile;
    private String pass;
    private String confirmPassword;
    private String country;
    
    private List<AddressRequest> addresses;
    
    private String gender;              // NEW
    private LocalDate dateOfBirth;
}
