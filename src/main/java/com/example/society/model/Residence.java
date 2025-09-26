package com.example.society.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Optional: If you use Lombok
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

@Entity
@Table(name = "residences")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor(exclude = "createdBy") // Exclude ManyToOne relationships from AllArgsConstructor if you use it
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Resident's full name

    @Column(nullable = false, length = 15)
    private String mobileNo;

    @Column(nullable = false, length = 15)
    private String pincode;

    @Column(name = "building_number", nullable = false)
    private String buildingNumber;

    @Column(name = "flat_number", nullable = false)
    private String flatNumber;

    @Column(name = "total_members")
    private Integer totalMembers;

    @Column(name = "vehicle_details")
    private String vehicleDetails;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    // REMOVED: @Column(nullable = false, length = 10)
    // REMOVED: private String pincode; // <--- REMOVE THIS FIELD

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // --- CONSTRUCTORS (if not using Lombok) ---
    public Residence() {
    }

    // --- UPDATED CONSTRUCTOR ---
    public Residence(String name, String mobileNo, String buildingNumber, String flatNumber,
                     Integer totalMembers, String vehicleDetails, String address, String city,
                     String state, // REMOVED: String pincode, // <--- REMOVE THIS PARAMETER
                     User createdBy) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.totalMembers = totalMembers;
        this.vehicleDetails = vehicleDetails;
        this.address = address;
        this.city = city;
        this.state = state;
        // REMOVED: this.pincode = pincode; // <--- REMOVE THIS ASSIGNMENT
        this.createdBy = createdBy;
    }


    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getBuildingNumber() { return buildingNumber; }
    public void setBuildingNumber(String buildingNumber) { this.buildingNumber = buildingNumber; }

    public String getFlatNumber() { return flatNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }

    public Integer getTotalMembers() { return totalMembers; }
    public void setTotalMembers(Integer totalMembers) { this.totalMembers = totalMembers; }

    public String getVehicleDetails() { return vehicleDetails; }
    public void setVehicleDetails(String vehicleDetails) { this.vehicleDetails = vehicleDetails; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPincode() { return pincode; }
    public void setPincode(String mobileNo) { this.mobileNo = mobileNo; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    // REMOVED: getPincode() and setPincode() methods // <--- REMOVE THESE METHODS
    /*
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    */

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
}