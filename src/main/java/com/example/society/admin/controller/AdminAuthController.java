package com.example.society.admin.controller;

import com.example.society.admin.dto.LoginRequest;
import com.example.society.admin.dto.SubAdminRegisterRequest;
import com.example.society.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



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
    try {
        ResponseEntity<?> response = adminService.login(loginRequest);
        
        if (!(response.getBody() instanceof Map)) {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Invalid login response",
                "data", null
            ));
        }

        Map<?, ?> body = (Map<?, ?>) response.getBody();
        String jwtToken = body.get("token") != null ? body.get("token").toString() : null;
        String username = body.get("username") != null ? body.get("username").toString() : null;

        if (jwtToken == null || jwtToken.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Token not found",
                "data", null
            ));
        }

        Map<String, Object> responseBody = Map.of(
            "success", true,
            "message", "Login successful",
            "data", Map.of(
                "token", jwtToken,
                "expiresIn", 3600,
                "username", username
            )
        );
        return ResponseEntity.ok(responseBody);
    } catch (Exception e) {
        return ResponseEntity.status(401).body(Map.of(
            "success", false,
            "message", "Login failed: " + e.getMessage(),
            "data", null
        ));
    }
}


    // Subadmin registration endpoint (superadmin only)
    @PostMapping("/register-subadmin")
    // @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> registerSubAdmin(@RequestBody SubAdminRegisterRequest registerRequest) {
        try {
            String message = adminService.registerSubAdmin(registerRequest);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error registering subadmin: " + e.getMessage());
        }
    }
}
