package com.ducdv.dudemy.domain.entity;

import com.ducdv.dudemy.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(columnDefinition = "jsonb")
    private String profileDetails;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
