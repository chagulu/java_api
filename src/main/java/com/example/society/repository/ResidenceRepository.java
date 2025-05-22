package com.example.society.repository;

import com.example.society.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResidenceRepository extends JpaRepository<Residence, Long>, JpaSpecificationExecutor<Residence> {
    Residence findByBuildingNumberAndFlatNumber(String buildingNumber, String flatNumber);
}
