package com.example.society.dto;

public class LoginRequest {
    private String mobileNo;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String mobileNo, String password) {
        this.mobileNo = mobileNo;
        this.password = password;
    }

    // Getters and Setters
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
