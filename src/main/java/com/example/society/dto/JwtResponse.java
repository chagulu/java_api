package com.example.society.dto;

public class JwtResponse {
    private String token;
    private boolean success;

    public JwtResponse(String token, boolean success) {
        this.token = token;
        this.success = success;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public boolean isSuccess() { return success; }

    public void setSuccess(boolean success) { this.success = success; }
}
