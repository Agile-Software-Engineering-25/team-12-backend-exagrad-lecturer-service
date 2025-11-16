package com.ase.lecturerservice.dtos;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDocumentResponse {
  private String uuid;
  private String feedbackId;
  private String lecturerId;
  private ZonedDateTime uploadDate;
  private String downloadUrl;
  private String fileName;
}

