package com.ducdv.dudemy.domain.entity;

import com.ducdv.dudemy.domain.enums.ContentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Enumerated(EnumType.STRING)
    private ContentType type;

    private String title;
    private String url;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    private Integer order;
    private LocalDateTime createdAt;
}
