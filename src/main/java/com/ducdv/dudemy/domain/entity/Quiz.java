package com.ducdv.dudemy.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private String title;
    private Integer totalQuestions;
    private LocalDateTime createdAt;
}
