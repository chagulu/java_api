package com.example.society.admin.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {
    private String username;
    private String password;
    private String role; // SUPER_ADMIN or SUB_ADMIN
}