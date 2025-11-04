package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import java.util.List;
import com.ase.lecturerservice.entities.ExamType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ExamServiceExamResponse {
  private boolean success;
  private int statusCode;
  private String status;
  private String message;
  private String timestamp;
  private String endpoint;
  private List<ExamServiceExamResponse.ExamServiceExamDto> data;
  private String error;


  @Data
  @NoArgsConstructor
  public static class ExamServiceExamDto {
    private String id;
    private String title;
    private String moduleCode;
    private LocalDate examDate;
    private String room;
    private ExamType examType;
    private String semester;
    private int etcs;
    private int maxPoints;
    private int duration;
    private int attemptNumber;
    private boolean fileUploadRequired;
    private String lecturerUuid;
    private List<String> tools;
  }
}
