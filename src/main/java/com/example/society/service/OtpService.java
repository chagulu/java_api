package com.example.society.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

            URI uri = UriComponentsBuilder.fromUriString("https://api.msg91.com/api/v5/otp")
                    .queryParam("template_id", "68087679d6fc054ae311ddc8")
                    .queryParam("mobile", fullMobile)
                    .queryParam("authkey", authKey)
                    .queryParam("otp", otp)
                    .queryParam("sender", senderId)
                    .build()
                    .toUri();

            Map response = restTemplate.getForObject(uri, Map.class);
            System.out.println("MSG91 OTP response: " + response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Send approval link SMS to resident using MSG91 API.
     * 
     * @param mobileNo Resident mobile number without country code
     * @param approvalLink Full or short approval URL
     * @return true if sent successfully, false otherwise
     */
    public boolean sendApprovalLink(String mobileNo, String approvalLink) {
        try {
            String fullMobile = "91" + mobileNo;

            // Replace with your actual template ID for approval link SMS
            String templateId = "YOUR_APPROVAL_LINK_TEMPLATE_ID";

            // IMPORTANT: 
            // MSG91 expects custom template variables sent differently (usually via JSON POST),
            // so check the API docs and adjust this if needed.

            URI uri = UriComponentsBuilder.fromUriString("https://api.msg91.com/api/v5/otp")
                    .queryParam("template_id", templateId)
                    .queryParam("mobile", fullMobile)
                    .queryParam("authkey", authKey)
                    .queryParam("sender", senderId)
                    .queryParam("approval_link", approvalLink) // This param name must match your template variable
                    .build()
                    .toUri();

            Map response = restTemplate.getForObject(uri, Map.class);
            System.out.println("MSG91 approval link response: " + response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
