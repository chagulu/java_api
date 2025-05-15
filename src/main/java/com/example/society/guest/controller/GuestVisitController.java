package com.example.society.guest.controller;

import com.example.society.guest.dto.GuestVisitRequest;
import com.example.society.guest.entity.Visitor;
import com.example.society.guest.repository.GuestEntryRepository;
import com.example.society.jwt.JwtUtil;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visitor")
@CrossOrigin(origins = GuestVisitController.ALLOWED_ORIGIN)
public class GuestVisitController {

    private static final Logger logger = LoggerFactory.getLogger(GuestVisitController.class);
    public static final String ALLOWED_ORIGIN = "http://localhost:8080"; // Change in one place only

    private final JwtUtil jwtUtil;
    private final GuestEntryRepository guestEntryRepository;

    public GuestVisitController(JwtUtil jwtUtil, GuestEntryRepository guestEntryRepository) {
        this.jwtUtil = jwtUtil;
        this.guestEntryRepository = guestEntryRepository;
    }

   @PostMapping("/entry")
public ResponseEntity<Map<String, Object>> recordGuestEntry(
        @RequestHeader(name = "Authorization", required = false) String authHeader,
        @Valid @RequestBody GuestVisitRequest request,
        BindingResult bindingResult) {

    logger.info("Received request to record guest entry");

    try {
        // Validate Authorization header presence and format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or malformed");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Missing or invalid Authorization header");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = authHeader.substring(7);

        // Validate JWT token
        if (!jwtUtil.validateToken(token)) {
            logger.warn("JWT token is invalid or expired");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            logger.warn("Validation errors: {}", errorMsg);

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", errorMsg);
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        // Extract user identifier (e.g., mobile number) from JWT token
        String userMobile = jwtUtil.extractUsername(token);
        logger.debug("Authenticated user mobile from token: {}", userMobile);

        // Build visitor entity
        Visitor visitor = new Visitor();
        visitor.setGuestName(request.getGuestName());
        visitor.setMobile(request.getMobile());
        visitor.setFlatNumber(request.getFlatNumber());
        visitor.setBuildingNumber(request.getBuildingNumber());
        visitor.setVisitPurpose(request.getVisitPurpose());
        visitor.setVehicleDetails(request.getVehicleDetails());
        visitor.setVisitTime(LocalDateTime.now());
        visitor.setCreatedBy(userMobile);

        // Save the entry
        guestEntryRepository.save(visitor);
        logger.info("Guest entry saved successfully for guest: {}", visitor.getGuestName());

        // Prepare response data
        Map<String, Object> visitorData = new HashMap<>();
        visitorData.put("guestName", visitor.getGuestName());
        visitorData.put("mobile", visitor.getMobile());
        visitorData.put("flatNumber", visitor.getFlatNumber());
        visitorData.put("buildingNumber", visitor.getBuildingNumber());
        visitorData.put("visitPurpose", visitor.getVisitPurpose());
        visitorData.put("vehicleDetails", visitor.getVehicleDetails());
        visitorData.put("visitTime", visitor.getVisitTime() != null ? visitor.getVisitTime().toString() : null);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Guest entry recorded successfully");
        response.put("data", visitorData);

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        logger.error("Error occurred while recording guest entry", e);
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Failed to record guest entry due to server error");
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}








    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }

    @PutMapping("/entry/{id}")
    public ResponseEntity<Map<String, Object>> editGuestEntry(
            @RequestHeader(name = "Authorization", required = false) String authHeader,
            @PathVariable Long id,
            @RequestBody GuestVisitRequest request) {

        logger.info("Received request to edit guest entry with ID: {}", id);

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Authorization header is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false,
                        "message", "Missing or invalid Authorization header",
                        "data", null
                ));
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                logger.warn("JWT token is invalid or expired: {}", token);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "success", false,
                        "message", "Invalid or expired token",
                        "data", null
                ));
            }

            String userMobile = jwtUtil.extractUsername(token);
            logger.debug("Authenticated user mobile from token: {}", userMobile);

            Visitor visitor = guestEntryRepository.findById(id).orElse(null);
            if (visitor == null) {
                logger.warn("Guest entry not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false,
                        "message", "Guest entry not found",
                        "data", null
                ));
            }

            visitor.setGuestName(request.getGuestName());
            visitor.setMobile(request.getMobile());
            visitor.setFlatNumber(request.getFlatNumber());
            visitor.setBuildingNumber(request.getBuildingNumber());
            visitor.setVisitPurpose(request.getVisitPurpose());
            visitor.setVehicleDetails(request.getVehicleDetails()); // ✅ Add this line
            //visitor.setUpdatedBy(userMobile);
            visitor.setVisitTime(LocalDateTime.now());

            guestEntryRepository.save(visitor);
            logger.info("Guest entry updated for: {}", visitor.getGuestName());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Guest entry updated successfully",
                    "data", visitor
            ));

        } catch (Exception e) {
            logger.error("Unexpected error while updating guest entry", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Failed to update guest entry",
                    "data", null
            ));
        }
    }

   
}
