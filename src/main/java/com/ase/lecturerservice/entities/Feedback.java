package com.ase.lecturerservice.entities;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Feedback {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String uuid;

  @Column(name = "graded_at")
  private LocalDate gradedAt;

  @Column(name = "graded_exam")
  @JsonProperty("examUuid")
  private String examUuid;

  @Column(name = "graded_by")
  @JsonProperty("lecturerUuid")
  private String lecturerUuid;

  @Column(name = "grade_for")
  @JsonProperty("studentUuid")
  private String studentUuid;

  @Column(name = "graded_submission")
  @JsonProperty("submissionUuid")
  private String submissionUuid;

  @Column(name = "comment")
  @JsonProperty("comment")
  private String comment;

  @ElementCollection
  @JsonProperty("fileReference")
  @CollectionTable(
      name = "feedback_file_references",
      joinColumns = @JoinColumn(name = "feedback_id")
  )
  private List<FileReference> fileReference;

  @Column(name = "points")
  @JsonProperty("points")
  private int points;

  @Column(name = "grade")
  @JsonProperty("grade")
  private float grade;
}
