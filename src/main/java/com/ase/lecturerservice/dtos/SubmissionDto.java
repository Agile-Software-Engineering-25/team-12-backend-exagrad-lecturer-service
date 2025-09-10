package com.ase.lecturerservice.dtos;

import java.util.List;
import com.ase.lecturerservice.entities.user.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionDto {
  @JsonProperty("totalPoints")
  private int totalPoints;

  @JsonProperty("student")
  private List<Student> student;

  @JsonProperty("grade")
  private List<GradeDto> grade;

}
