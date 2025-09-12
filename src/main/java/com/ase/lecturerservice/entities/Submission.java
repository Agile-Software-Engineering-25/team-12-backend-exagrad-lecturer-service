package com.ase.lecturerservice.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Submission {
  private String id;
  private String examId;
  private String studentId;
  private String submissionDate;
  private FileReference fileUpload;
}
