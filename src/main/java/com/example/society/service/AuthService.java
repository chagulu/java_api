package com.example.society.service;

import com.example.society.dto.ResidenceRegisterRequest;
import com.example.society.model.Residence;
import com.example.society.model.User;
import com.example.society.repository.ResidenceRepository;
import com.example.society.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ResidenceRepository residenceRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, ResidenceRepository residenceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.residenceRepository = residenceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerResident(ResidenceRegisterRequest request) {
        // Check if user with mobile number already exists
        if (userRepository.findByMobileNo(request.getMobileNo()).isPresent()) {
            return ResponseEntity.badRequest().body("User with this mobile number already exists.");
        }

        // Create and save the user
        User user = new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getMobileNo(),
            null
        );
        userRepository.save(user);

        // Create and save the residence
        Residence residence = new Residence();
        residence.setName(request.getResidenceName());
        residence.setAddress(request.getAddress());
        residence.setCreatedBy(user);
        residenceRepository.save(residence);

        return ResponseEntity.ok("Resident registered successfully.");
    }
}
