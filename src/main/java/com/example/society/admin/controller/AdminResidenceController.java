package com.example.society.admin.controller;

import com.example.society.dto.ResidenceDTO;
import com.example.society.dto.ResidenceRegisterRequest;
// No need to import ErrorResponse or custom exceptions here anymore
import com.example.society.model.Residence;
import com.example.society.service.AuthService;
import com.example.society.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/residences")
public class AdminResidenceController {

    private final ResidenceService residenceService;
    private final AuthService authService;

    @Autowired
    public AdminResidenceController(ResidenceService residenceService, AuthService authService) {
        this.residenceService = residenceService;
        this.authService = authService;
    }

    // === API: Get filtered list of residences ===
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
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
            // This catch block is for general service layer exceptions in getFilteredResidences
            // For specific business logic errors, consider throwing custom exceptions
            // and handling them in GlobalExceptionHandler as well.
            System.err.println("Error fetching residences: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Or return a generic error DTO
        }
    }

    // === API: Register a new resident (admin only) ===
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-resident")
    public ResponseEntity<ResidenceDTO> registerResident(@RequestBody ResidenceRegisterRequest request) {
        // The service method now throws UserAlreadyExistsException,
        // which will be caught by the GlobalExceptionHandler.
        // For success, it returns Residence object, which we map to DTO.
        Residence registeredResidence = authService.registerResident(request);
        // HttpStatus.CREATED (201) is typically used for successful resource creation
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResidenceDTO(registeredResidence));
    }
}