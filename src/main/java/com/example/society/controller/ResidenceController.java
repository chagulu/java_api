package com.example.society.controller;

import com.example.society.dto.ResidenceDTO;
import com.example.society.model.Residence;
import com.example.society.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/residences")
public class ResidenceController {

    private final ResidenceService residenceService;

    @Autowired
    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    // Add JWT authentication handling with @PreAuthorize (or you can also use @Secured)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResidenceDTO>> getAllResidences() {
        try {
            // Fetch all residences from the service layer
            List<Residence> residences = residenceService.getAllResidences();
            
            // Convert Residence entities to ResidenceDTO
            List<ResidenceDTO> residenceDTOs = residences.stream()
                .map(ResidenceDTO::new) // Use ResidenceDTO constructor to convert
                .collect(Collectors.toList());

            return ResponseEntity.ok(residenceDTOs); // Return response with DTOs
        } catch (Exception e) {
            // If something goes wrong, return internal server error
            return ResponseEntity.status(500).body(null);
        }
    }
}
