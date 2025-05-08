package com.example.society.controller;

import com.example.society.service.OtpService;
import com.example.society.dto.OtpRequest;
import com.example.society.dto.UserDto;
import com.example.society.model.User;
import com.example.society.repository.UserRepository;
import com.example.society.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpService otpService;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    // ✅ Register API
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDto userDto) {
        try {
            if (userRepository.existsByUsername(userDto.getUsername())) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Username already exists"
                ));
            }

            if (userRepository.existsByMobileNo(userDto.getMobileNo())) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Mobile number already registered"
                ));
            }

            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setMobileNo(userDto.getMobileNo());
            user.setEmail(userDto.getEmail());

            // Residence details (new fields)
            user.setResidenceName(userDto.getResidenceName());
            user.setFlatNumber(userDto.getFlatNumber());
            user.setBuildingNumber(userDto.getBuildingNumber());

            userRepository.save(user);

            String token = jwtService.generateToken(user.getMobileNo());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Registration failed",
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        String mobileNo = request.get("mobileNo");

        Optional<User> userOpt = userRepository.findByMobileNo(mobileNo);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Mobile number not registered"
            ));
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        otpStore.put(mobileNo, otp);

        boolean sent = otpService.sendOtp(mobileNo, otp);

        if (sent) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP sent"
            ));
        } else {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Failed to send OTP"
            ));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody OtpRequest otpRequest) {
        String sentOtp = otpStore.get(otpRequest.getMobileNo());

        if (sentOtp != null && sentOtp.equals(otpRequest.getOtp())) {
            String token = jwtService.generateToken(otpRequest.getMobileNo());
            otpStore.remove(otpRequest.getMobileNo());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token
            ));
        }

        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid OTP"
        ));
    }
}
