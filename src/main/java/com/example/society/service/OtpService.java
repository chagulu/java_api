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

    /**
     * Send approval link SMS to resident using MSG91 API.
     * 
     * @param mobileNo Resident mobile number without country code
     * @param approvalLink Short or full approval URL
     * @return true if sent successfully, false otherwise
     */
    public boolean sendApprovalLink(String mobileNo, String approvalLink) {
        try {
            String fullMobile = "91" + mobileNo;

            // Customize your template_id for approval link if you have one, else reuse OTP template with params
            String templateId = "YOUR_APPROVAL_LINK_TEMPLATE_ID"; // Replace with your real template ID

            // Build URL with query params
            String url = "https://api.msg91.com/api/v5/otp" +
                    "?template_id=" + templateId +
                    "&mobile=" + fullMobile +
                    "&authkey=" + authKey +
                    "&sender=" + senderId +
                    // Assuming your template supports a param named 'approval_link'
                    "&approval_link=" + approvalLink;

            Map response = restTemplate.getForObject(url, Map.class);
            System.out.println("MSG91 approval link response: " + response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
