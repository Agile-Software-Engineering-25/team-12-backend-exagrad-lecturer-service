package com.ase.lecturerservice.dtos;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
  @NoArgsConstructor
  public static class StudentServiceSubmissionDto {
    private String id;
    private String examId;
    private String studentId;
    private String uploadDate;
    private String downloadUrl;
    private String fileName;
  }
}
