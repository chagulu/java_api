package com.example.society.dto;

public class ResidenceRegisterRequest {
    private String username;
    private String password;
    private String mobileNo;
    private String residenceName;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String buildingNumber;
    private String flatNumber;

    public ResidenceRegisterRequest() {}

    public ResidenceRegisterRequest(String username, String password, String mobileNo, String residenceName, String address,
                                    String city, String state, String pincode, String buildingNumber, String flatNumber) {
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.residenceName = residenceName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }
}
