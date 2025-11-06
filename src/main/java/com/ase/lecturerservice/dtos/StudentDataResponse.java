package com.ase.lecturerservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentDataResponse {
  private boolean success;
  private int statusCode;
  private String status;
  private String message;
  private String timestamp;
  private String endpoint;
  private List<StudentDataResponse.StudentDto> data;
  private String error;


  @Data
  @NoArgsConstructor
  public static class StudentDto {
    private String id;
    private String matriculationNumber;
  }
}
