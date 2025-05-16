package com.example.society.controller;

import com.example.society.guest.entity.Visitor;
import com.example.society.repository.VisitorRepository;
import com.example.society.service.OtpService;
import com.example.society.service.ResidenceService;
import com.example.society.specification.VisitorSpecification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorRepository visitorRepository;
    private final ResidenceService residentService;
    private final OtpService otpService;

    @Value("${app.base-url}")
    private String baseUrl;

    public VisitorController(VisitorRepository visitorRepository, ResidenceService residentService, OtpService otpService) {
        this.visitorRepository = visitorRepository;
        this.residentService = residentService;
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

    // ✅ NEW: Approve visitor by token and sender
    @GetMapping("/approve")
    public ResponseEntity<String> approveVisitor(
            @RequestParam("token") String token,
            @RequestParam("sender") String sender) {

        // You can implement lookup and approval logic here
        // For example:
        Optional<Visitor> visitorOpt = visitorRepository.findByToken(token);

        if (visitorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        Visitor visitor = visitorOpt.get();
        visitor.setApproveStatus(Visitor.ApproveStatus.APPROVED);
        visitorRepository.save(visitor);

        return ResponseEntity.ok("Visitor approved successfully by " + sender);
    }
}
