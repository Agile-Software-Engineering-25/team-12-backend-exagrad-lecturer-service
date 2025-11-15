package com.ase.lecturerservice.entities;

import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDocument {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

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
