package com.ducdv.dudemy.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private String instructions;
    private LocalDateTime dueDate;
    private Integer maxScore;
    private LocalDateTime createdAt;
}
