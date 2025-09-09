package com.ase.lecturerservice.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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
  @UuidGenerator
  protected  UUID uuid;

  @Column(name = "graded_at")
  protected LocalDate date;

  @Column(name = "graded_by")
  protected UUID lecturerUuid;

  @Column(name = "grade_for")
  protected UUID studentUuid;

  @Column(name = "graded_submission")
  protected UUID submissionUuid;

  @Column(name = "comment")
  protected String comment;

  @ElementCollection
  @CollectionTable(
      name = "grade_file_references",
      joinColumns = @JoinColumn(name = "grade_id")
  )
  protected List<FileReference> fileReference;

  @Column(name = "points")
  protected int points;

  @Column(name = "grade")
  protected float grade;
}
