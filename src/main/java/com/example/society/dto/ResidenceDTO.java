package com.example.society.dto;

import com.example.society.model.Residence;

public class ResidenceDTO {
    private Long id;
    private String name;
    private String mobileNo;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String buildingNumber; // ✅ New field
    private String flatNumber;     // ✅ New field
    private Long createdById;

    public ResidenceDTO(Residence res) {
        this.id = res.getId();
        this.name = res.getName();
        this.mobileNo = res.getMobileNo();
        this.address = res.getAddress();
        this.city = res.getCity();
        this.state = res.getState();
        this.pincode = res.getPincode();
        this.buildingNumber = res.getBuildingNumber(); // ✅ Set value
        this.flatNumber = res.getFlatNumber();         // ✅ Set value
        this.createdById = res.getCreatedBy() != null ? res.getCreatedBy().getId() : null;
    }

    // ✅ Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMobileNo() { return mobileNo; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPincode() { return pincode; }
    public String getBuildingNumber() { return buildingNumber; } // ✅ Getter
    public String getFlatNumber() { return flatNumber; }         // ✅ Getter
    public Long getCreatedById() { return createdById; }
}
