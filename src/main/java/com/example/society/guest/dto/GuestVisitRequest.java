package com.example.society.guest.dto;

import lombok.Data;

@Data
public class GuestVisitRequest {
    private String guestName;
    private String mobile;
    private String flatNumber;
    private String buildingNumber;
    private String visitPurpose;
}
