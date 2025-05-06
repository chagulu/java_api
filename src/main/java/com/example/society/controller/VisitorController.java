package com.example.society.controller;
import com.example.society.guest.entity.Visitor;
import com.example.society.repository.VisitorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.example.society.specification.VisitorSpecification;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorRepository visitorRepository;

    public VisitorController(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVisitors(
            @RequestParam(required = false) String guestName,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String flatNumber,
            @RequestParam(required = false) String buildingNumber,
            @RequestParam(defaultValue = "0") int page
    ) {
        // Build filter map, handle nulls in filters
        Map<String, String> filters = new HashMap<>();
        if (guestName != null) filters.put("guestName", guestName);
        if (mobile != null) filters.put("mobile", mobile);
        if (flatNumber != null) filters.put("flatNumber", flatNumber);
        if (buildingNumber != null) filters.put("buildingNumber", buildingNumber);

        // Pageable configuration, with sorting by createdAt in descending order
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdAt")));

        // Apply filters with pagination
        Page<Visitor> result = visitorRepository.findAll(
                VisitorSpecification.getVisitorFilters(filters),
                pageable
        );

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("visitors", result.getContent());
        response.put("currentPage", result.getNumber());
        response.put("totalPages", result.getTotalPages());
        response.put("totalItems", result.getTotalElements());

        return ResponseEntity.ok(response);
    }
}
