package com.ase.lecturerservice.dtos;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradeDto {
  @NotNull
  private float grade;

  @NotNull
  private int points;
}
