package com.ducdv.dudemy.domain.entity;

import com.ducdv.dudemy.domain.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String questionText;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(columnDefinition = "jsonb")
    private String options;

    @Column(columnDefinition = "jsonb")
    private String correctAnswer;
}
