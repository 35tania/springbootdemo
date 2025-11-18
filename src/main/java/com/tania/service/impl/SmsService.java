package com.tania.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

    @Value("${textbee.apiKey}")
    private String apiKey;

    @Value("${textbee.deviceId}")
    private String deviceId;

    private final RestTemplate restTemplate = new RestTemplate();

//    public void sendOtpSms(String mobile, String otp) {
//
//        String url = "https://api.textbee.dev/api/v1/gateway/devices/" + deviceId + "/send-sms";
//
//        try {
//            Map<String, Object> body = new HashMap<>();
//            body.put("recipients", new String[]{ mobile });
//            body.put("message", "Your OTP is: " + otp);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("x-api-key", apiKey);
//
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                throw new RuntimeException("Failed to send SMS: " + response.getBody());
//            }
//
//            System.out.println("SMS sent successfully using TextBee!");
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error sending SMS: " + e.getMessage());
//        }
//    }
    
    public void sendOtpSms(String mobile, String otp) {

        String url = "https://api.textbee.dev/api/v1/gateway/devices/" 
                     + deviceId + "/send-sms";

        try {
            String message =
                    "Welcome to FirgoMart\n" +
                    "Your verification code is: " + otp + "\n\n" +
                    "This code will expire in 10 minutes.\n" +
                    "Do not share it with anyone.\n\n" ;
                    

            Map<String, Object> body = new HashMap<>();
            body.put("recipients", new String[]{ mobile });
            body.put("message", message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to send SMS: " + response.getBody());
            }

            System.out.println("Firgomart OTP SMS sent successfully!");

        } catch (Exception e) {
            throw new RuntimeException("Error sending SMS: " + e.getMessage());
        }
    }


}
