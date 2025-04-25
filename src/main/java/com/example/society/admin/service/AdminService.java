package com.example.society.admin.service;

import com.example.society.admin.dto.AdminDto;
import com.example.society.admin.dto.LoginRequest;
import com.example.society.admin.entity.Admin;
import com.example.society.admin.entity.LoginLog;
import com.example.society.admin.repository.AdminRepository;
import com.example.society.admin.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Autowired
    public AdminService(@Qualifier("adminPasswordEncoder") PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;

    public String authenticate(AdminDto adminDto) {
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(adminDto.getUsername());
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            if (passwordEncoder.matches(adminDto.getPassword(), admin.getPassword())) {
                logLogin(admin.getUsername());
                return "Login successful";
            }
        }
        return "Invalid credentials";
    }

    public String createSubAdmin(AdminDto dto) {
        Admin admin = Admin.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Admin.Role.SUB_ADMIN)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
        adminRepository.save(admin);
        return "Sub-admin created";
    }

    private void logLogin(String username) {
        LoginLog log = LoginLog.builder()
                .username(username)
                .loginTime(LocalDateTime.now())
                .ipAddress("127.0.0.1") // placeholder
                .build();
        loginLogRepository.save(log);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(loginRequest.getUsername());
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
                logLogin(admin.getUsername());
                return ResponseEntity.ok("Login successful");
            }
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
