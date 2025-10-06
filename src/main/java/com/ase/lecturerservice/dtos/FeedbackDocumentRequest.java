package com.ase.lecturerservice.dtos;

import lombok.Data;

@Data
public class FeedbackDocumentRequest {
  private String feedbackId;
  private String lecturerId;
}
