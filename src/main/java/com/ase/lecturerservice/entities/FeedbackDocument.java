package com.ase.lecturerservice.entities;

import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
  private String uuid;

  @PrePersist
  protected void onCreate() {
    if (this.uuid == null) {
      this.uuid = UUID.randomUUID().toString();
    }
  }

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
