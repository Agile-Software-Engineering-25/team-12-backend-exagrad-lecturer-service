package com.ase.lecturerservice.dtos;

import lombok.Data;
import java.util.List;

@Data
public class StudentServiceSubmissionResponse {
  private boolean success;
  private int statusCode;
  private String status;
  private String message;
  private String timestamp;
  private String endpoint;
  private List<StudentServiceSubmissionDto> data;
  private String error;

  @Data
  public class StudentServiceSubmissionDto {
    private String id;
    private String examId;
    private String studentId;
    private String uploadDate;
    private String downloadUrl;
    private String fileName;
  }
}
