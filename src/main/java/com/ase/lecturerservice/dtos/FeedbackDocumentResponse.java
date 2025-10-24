package com.ase.lecturerservice.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackDocumentResponse {
  private UUID id;
  private String feedbackId;
  private String lecturerId;
  private ZonedDateTime uploadDate;
  private String downloadUrl;
  private String fileName;
}
