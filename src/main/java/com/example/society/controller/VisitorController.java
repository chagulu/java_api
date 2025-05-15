package com.example.society.controller;

import com.example.society.guest.entity.Visitor;
import com.example.society.repository.VisitorRepository;
import com.example.society.specification.VisitorSpecification;

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

    public VisitorController(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVisitors(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String guestName,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String flatNumber,
            @RequestParam(required = false) String buildingNumber,
            @RequestParam(required = false) Visitor.ApproveStatus approveStatus, // ✅ New filter
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

        // Build filter map
        Map<String, String> filters = new HashMap<>();
        if (guestName != null) filters.put("guestName", guestName);
        if (mobile != null) filters.put("mobile", mobile);
        if (flatNumber != null) filters.put("flatNumber", flatNumber);
        if (buildingNumber != null) filters.put("buildingNumber", buildingNumber);
        if (approveStatus != null) filters.put("approveStatus", approveStatus.name()); // ✅ include filter

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdAt")));

        Page<Visitor> result = visitorRepository.findAll(
                VisitorSpecification.getVisitorFilters(filters),
                pageable
        );

        response.put("visitors", result.getContent()); // approveStatus will be part of each Visitor object
        response.put("currentPage", result.getNumber());
        response.put("totalPages", result.getTotalPages());
        response.put("totalItems", result.getTotalElements());

        return ResponseEntity.ok(response);
    }
}
