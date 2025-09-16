package com.ase.lecturerservice.dtos;

import com.ase.lecturerservice.entities.Submission;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamSubmissionDTO {

  @NotNull
  private String submissionId;

  @NotNull
  private String examId;

  @NotNull
  private String studentId;

  @NotNull
  private String fileUrl;

  @NotNull
  private Submission.status status;

  @NotNull
  private float grade;

}
