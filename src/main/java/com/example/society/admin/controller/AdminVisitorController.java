package com.example.society.admin.controller;

import com.example.society.guest.entity.Visitor;
import com.example.society.repository.VisitorRepository;
import com.example.society.service.ResidenceService;
import com.example.society.specification.VisitorSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/visitors")
public class AdminVisitorController {

    private static final Logger logger = LoggerFactory.getLogger(AdminVisitorController.class);

    private final VisitorRepository visitorRepository;
    private final ResidenceService residenceService;

    public AdminVisitorController(VisitorRepository visitorRepository, ResidenceService residenceService) {
        this.visitorRepository = visitorRepository;
        this.residenceService = residenceService;
    }

    @GetMapping
public ResponseEntity<Map<String, Object>> getAllVisitors(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String guestName,
        @RequestParam(required = false) String mobile,
        @RequestParam(required = false) String flatNo,
        @RequestParam(required = false) String buildingNo,
        @RequestParam(required = false) Visitor.ApproveStatus approveStatus,
        @RequestParam(defaultValue = "0") int page
) {
    logger.info("Admin request to fetch visitors");

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
    if (flatNo != null) filters.put("flatNumber", flatNo);
    if (buildingNo != null) filters.put("buildingNumber", buildingNo);
    if (approveStatus != null) filters.put("approveStatus", approveStatus.name());

    int adjustedPage = (page > 0) ? page - 1 : 0;

    Pageable pageable = PageRequest.of(adjustedPage, 10, Sort.by(Sort.Order.desc("createdAt")));

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

}
