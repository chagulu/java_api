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
    private Long createdById;

    public ResidenceDTO(Residence res) {
        this.id = res.getId();
        this.name = res.getName();
        this.mobileNo = res.getMobileNo();
        this.address = res.getAddress();
        this.city = res.getCity();
        this.state = res.getState();
        this.pincode = res.getPincode();
        this.createdById = res.getCreatedBy() != null ? res.getCreatedBy().getId() : null;
    }

    // ✅ Add getters if you're not using Lombok
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMobileNo() { return mobileNo; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPincode() { return pincode; }
    public Long getCreatedById() { return createdById; }
}
