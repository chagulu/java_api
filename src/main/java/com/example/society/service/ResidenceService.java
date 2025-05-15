package com.example.society.service;

import com.example.society.model.Residence;
import com.example.society.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidenceService {

    private final ResidenceRepository residenceRepository;

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository) {
        this.residenceRepository = residenceRepository;
    }

    public List<Residence> getAllResidences() {
        return residenceRepository.findAll();
    }

    /**
     * Returns the mobile number of the resident based on building and flat number.
     */
    public String getResidentMobile(String buildingNumber, String flatNumber) {
        Residence residence = residenceRepository.findByBuildingNumberAndFlatNumber(buildingNumber, flatNumber);
        if (residence != null) {
            return residence.getMobileNo();
        }
        return null;
    }
}
