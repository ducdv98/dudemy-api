package com.ducdv.dudemy.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private String fileUrl;
    private String fileName;
    private String fileType;
    private LocalDateTime createdAt;
}
