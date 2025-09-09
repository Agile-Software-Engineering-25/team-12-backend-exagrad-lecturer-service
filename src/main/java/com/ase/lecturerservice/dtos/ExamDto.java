package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import java.util.UUID;
import com.ase.lecturerservice.entities.ExamType;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDto {
  @NotNull
  private UUID uuid;

  @NotNull
  private String name;

  @NotNull
  private LocalDate date;

  @NotNull
  private String module;

  @NotNull
  private int time;

  @NotNull
  private ExamType examType;

  private int submissionsCount;
}
