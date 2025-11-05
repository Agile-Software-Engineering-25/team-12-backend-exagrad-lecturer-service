package com.ase.lecturerservice.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ExamServiceStudentResponse {
  private boolean success;
  private int statusCode;
  private String status;
  private String message;
  private String timestamp;
  private String endpoint;
  private List<ExamServiceStudentResponse.ExamServiceStudentDto> data;
  private String error;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ExamServiceStudentDto {
    private String id;
    private String matriculationId;
    private String firstName;
    private String lastName;
    private String email;
    private String studyGroup;
    private Integer semester;
    private String fullName;
    private List<String> examIds;
  }
}
