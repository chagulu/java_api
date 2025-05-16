package com.example.society.controller;

import com.example.society.guest.entity.Visitor;
import com.example.society.repository.VisitorRepository;
import com.example.society.service.OtpService;
import com.example.society.service.ResidenceService;
import com.example.society.specification.VisitorSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    private static final Logger logger = LoggerFactory.getLogger(VisitorController.class);

    private final VisitorRepository visitorRepository;
    private final ResidenceService residenceService;
    private final OtpService otpService;

    @Value("${app.base-url}")
    private String baseUrl;

    public VisitorController(VisitorRepository visitorRepository, ResidenceService residenceService, OtpService otpService) {
        this.visitorRepository = visitorRepository;
        this.residenceService = residenceService;
        this.otpService = otpService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVisitors(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String guestName,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String flatNumber,
            @RequestParam(required = false) String buildingNumber,
            @RequestParam(required = false) Visitor.ApproveStatus approveStatus,
            @RequestParam(defaultValue = "0") int page
    ) {
        Map<String, Object> response = new HashMap<>();

        if (id != null) {
            Optional<Visitor> visitorOpt = visitorRepository.findById(id);
            List<Visitor> visitors = visitorOpt.map(List::of).orElse(Collections.emptyList());

            response.put("visitors", visitors);
            response.put("currentPage", 0);
            response.put("totalPages", 1);
            response.put("totalItems", visitors.size());

            return ResponseEntity.ok(response);
        }

        Map<String, String> filters = new HashMap<>();
        if (guestName != null) filters.put("guestName", guestName);
        if (mobile != null) filters.put("mobile", mobile);
        if (flatNumber != null) filters.put("flatNumber", flatNumber);
        if (buildingNumber != null) filters.put("buildingNumber", buildingNumber);
        if (approveStatus != null) filters.put("approveStatus", approveStatus.name());

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdAt")));

        Page<Visitor> result = visitorRepository.findAll(
                VisitorSpecification.getVisitorFilters(filters),
                pageable
        );

        response.put("visitors", result.getContent());
        response.put("currentPage", result.getNumber());
        response.put("totalPages", result.getTotalPages());
        response.put("totalItems", result.getTotalElements());

        return ResponseEntity.ok(response);
    }

    // ✅ Updated: Approve visitor by token
    @GetMapping("/approve")
    public ResponseEntity<Map<String, Object>> approveVisitor(@RequestParam("token") String token) {
        logger.info("Received approval request for token: {}", token);

        Map<String, Object> response = new HashMap<>();

        Optional<Visitor> visitorOpt = visitorRepository.findByToken(token);

        if (visitorOpt.isEmpty()) {
            logger.warn("Invalid or expired token used: {}", token);
            response.put("success", false);
            response.put("message", "Invalid or expired approval token.");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        Visitor visitor = visitorOpt.get();
        visitor.setApproveStatus(Visitor.ApproveStatus.APPROVED);
        visitorRepository.save(visitor);

        logger.info("Visitor with token {} approved successfully", token);

        Map<String, Object> visitorData = new HashMap<>();
        visitorData.put("guestName", visitor.getGuestName());
        visitorData.put("mobile", visitor.getMobile());
        visitorData.put("flatNumber", visitor.getFlatNumber());
        visitorData.put("buildingNumber", visitor.getBuildingNumber());
        visitorData.put("visitTime", visitor.getVisitTime());

        response.put("success", true);
        response.put("message", "Visitor approved successfully.");
        response.put("data", visitorData);

        return ResponseEntity.ok(response);
    }
}
