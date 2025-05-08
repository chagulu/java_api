package com.example.society.dto;

public class ResidenceRegisterRequest {
    private String username;
    private String password;
    private String mobileNo;
    private String residenceName;
    private String address;

    public ResidenceRegisterRequest() {}

    public ResidenceRegisterRequest(String username, String password, String mobileNo, String residenceName, String address) {
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.residenceName = residenceName;
        this.address = address;
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
}
