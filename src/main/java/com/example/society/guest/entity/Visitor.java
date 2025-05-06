package com.example.society.guest.entity;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
@Data
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;
    private String mobile;
    private String flatNumber;
    private String buildingNumber;
    private String visitPurpose;
    private LocalDateTime visitTime;
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // ✅ Mapped to the existing column
}
