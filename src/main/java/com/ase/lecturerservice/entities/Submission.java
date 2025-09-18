package com.ase.lecturerservice.entities;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Submission {
  private String id;
  private String examId;
  private String studentId;
  private String submissionDate;
  private List<FileReference> fileUpload;
}
