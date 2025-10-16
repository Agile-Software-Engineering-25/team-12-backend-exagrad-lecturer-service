package com.ase.lecturerservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "feedback_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDocument {

    @Id @GeneratedValue private UUID id;

    @Column(nullable = false)
    private String feedbackId;

    @Column(nullable = false)
    private String lecturerId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant uploadDate;

    @Column(nullable = false, unique = true)
    private String minioKey;

    @Column(nullable = false)
    private String fileName;
}
