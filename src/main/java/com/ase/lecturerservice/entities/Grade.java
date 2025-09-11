package com.ase.lecturerservice.entities;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grade {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String uuid;

  @Column(name = "graded_at")
  private LocalDate gradedAt;

  @Column(name = "graded_exam")
  private String examUuid;

  @Column(name = "graded_by")
  private String lecturerUuid;

  @Column(name = "grade_for")
  private String studentUuid;

  @Column(name = "graded_submission")
  private String submissionUuid;

  @Column(name = "comment")
  private String comment;

  @ElementCollection
  @CollectionTable(
      name = "grade_file_references",
      joinColumns = @JoinColumn(name = "grade_id")
  )
  private List<FileReference> fileReference;

  @Column(name = "points")
  private int points;

  @Column(name = "grade")
  private float grade;
}
