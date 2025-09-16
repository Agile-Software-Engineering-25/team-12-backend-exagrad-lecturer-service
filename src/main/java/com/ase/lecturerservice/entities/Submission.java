package com.ase.lecturerservice.entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Submission {

  public enum status {
    submitted, rejected
  }

  protected String submissionId;
  protected String examId;
  protected String studentId;
  protected Date submissionDate;
  protected String fileUrl;
  protected status status;
  protected String examOfficeComment;
  protected float grade;
  protected String gradeDocumentUrl;
  protected String graderId;
  protected Date gradingDate;

}
