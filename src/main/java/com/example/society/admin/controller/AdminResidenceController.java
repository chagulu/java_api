package com.example.society.admin.controller;

import com.example.society.dto.ResidenceDTO;
import com.example.society.model.Residence;
import com.example.society.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/residences")
public class AdminResidenceController {

    private final ResidenceService residenceService;

    @Autowired
    public AdminResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    // Thymeleaf page
    @GetMapping
    public String showResidenceListingPage() {
        return "admin/includes/residence-list";
    }

    // REST API: filtered, paged residences for admin token only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Page<ResidenceDTO>> getFilteredResidences(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String mobileNo,
            @RequestParam(required = false) String flatNumber,
            @RequestParam(required = false) String buildingNumber,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        try {
            Page<Residence> residences = residenceService.findFiltered(name, mobileNo, flatNumber, buildingNumber, pageable);
            Page<ResidenceDTO> residenceDTOs = residences.map(ResidenceDTO::new);
            return ResponseEntity.ok(residenceDTOs);
        } catch (Exception e) {
            // You can log the exception here if you want
            return ResponseEntity.status(500).build();
        }
    }
}
