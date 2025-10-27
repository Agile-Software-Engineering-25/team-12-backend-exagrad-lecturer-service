package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackRequest {

  private LocalDate gradedAt;
  private String examUuid;
  private String lecturerUuid;
  private String studentUuid;
  private String submissionUuid;
  private String comment;
  private int points;
  private float grade;
}
