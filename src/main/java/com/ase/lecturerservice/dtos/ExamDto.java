package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import java.util.List;
import org.antlr.v4.runtime.misc.NotNull;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.user.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDto {
  @NotNull
  private String uuid;

  @NotNull
  private String name;

  @NotNull
  private LocalDate date;

  @NotNull
  private String module;

  @NotNull
  private ExamType examType;

  @NotNull
  private List<Student> assignedStudents;

  @NotNull
  private int time;

  @NotNull
  private int totalPoints;

  @NotNull
  private boolean fileUploadRequired;
}
