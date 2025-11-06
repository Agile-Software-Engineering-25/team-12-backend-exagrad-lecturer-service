package com.ase.lecturerservice.dtos;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamServiceStudentIdResponse {
  private boolean success;
  private int statusCode;
  private String status;
  private String message;
  private String timestamp;
  private String endpoint;
  private List<String> data;
  private String error;
}
