package com.example.society.model;

import jakarta.persistence.*;

@Entity
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "mobile_no", nullable = false, unique = true, length = 15)
    private String mobileNo;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 10)
    private String pincode;

    @Column(name = "building_number", nullable = false)
    private String buildingNumber;

    @Column(name = "flat_number", nullable = false)
    private String flatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // Constructors
    public Residence() {
    }

    public Residence(String name, String mobileNo, String address, String city, String state, String pincode,
                     String buildingNumber, String flatNumber, User createdBy) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.createdBy = createdBy;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
