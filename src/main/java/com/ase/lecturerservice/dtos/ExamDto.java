package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ExamDto {
  @NonNull
  private UUID uuid;

  @NonNull
  private String name;

  @NonNull
  private LocalDate date;

  @NonNull
  private String module;

  private int time;

  private int submissionsCount;
}
