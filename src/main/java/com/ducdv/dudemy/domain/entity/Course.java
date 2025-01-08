package com.ducdv.dudemy.domain.entity;

import com.ducdv.dudemy.domain.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
