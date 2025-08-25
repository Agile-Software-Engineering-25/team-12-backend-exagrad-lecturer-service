package com.ase.lecturerservice.dtos;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

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
