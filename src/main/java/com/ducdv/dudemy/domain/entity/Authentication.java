package com.ducdv.dudemy.domain.entity;

import com.ducdv.dudemy.domain.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "authentication")
public class Authentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerUserId;
    private String passwordHash;
    private LocalDateTime createdAt;
}
