package com.ase.lecturerservice.dtos;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDto {
  @NotNull
  private String name;

  @NotNull
  private LocalDate date;

  @NotNull
  private String module;

  @NotNull
  private int time;

  private int submissions;
}
