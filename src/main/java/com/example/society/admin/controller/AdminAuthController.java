package com.example.society.admin.controller;

import com.example.society.admin.dto.LoginRequest;
import com.example.society.admin.dto.SubAdminRegisterRequest;
import com.example.society.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminService adminService;

    public AdminAuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Superadmin login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return adminService.login(loginRequest);
    }

    // Subadmin registration endpoint (superadmin only)
    @PostMapping("/register-subadmin")
    public ResponseEntity<?> registerSubAdmin(@RequestBody SubAdminRegisterRequest registerRequest) {
        try {
            String message = adminService.registerSubAdmin(registerRequest);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error registering subadmin: " + e.getMessage());
        }
    }
}
