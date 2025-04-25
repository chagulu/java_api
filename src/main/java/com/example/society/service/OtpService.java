package com.example.society.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OtpService {

    @Value("${msg91.authkey}")
    private String authKey;

    @Value("${msg91.senderId}")
    private String senderId;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean sendOtp(String mobileNo, String otp) {
        try {
            String fullMobile = "91" + mobileNo; // Add country code without '+'
    
            String url = "https://api.msg91.com/api/v5/otp" +
                    "?template_id=68087679d6fc054ae311ddc8" +
                    "&mobile=" + fullMobile +
                    "&authkey=" + authKey +
                    "&otp=" + otp +
                    "&sender=" + senderId;
    
            Map response = restTemplate.getForObject(url, Map.class);
            System.out.println("MSG91 response: " + response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
