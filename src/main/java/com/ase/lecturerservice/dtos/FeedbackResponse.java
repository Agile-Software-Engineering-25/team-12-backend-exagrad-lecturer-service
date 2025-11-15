package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import java.util.List;
import com.ase.lecturerservice.entities.PublishStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {
  private String uuid;
  private LocalDate gradedAt;
  private String examUuid;
  private String lecturerUuid;
  private String studentUuid;
  private String submissionUuid;
  private String comment;
  private int points;
  private float grade;
  private PublishStatus publishStatus;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<FeedbackDocumentResponse> fileReference;
}

