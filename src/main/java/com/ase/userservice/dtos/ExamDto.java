package com.ase.userservice.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Date;

@Data
public class ExamDto {
  @NotNull
  private String name;

  @NotNull
  private Date date;

  @NotNull
  private String module;

  @NotNull
  private int time;

  @NotNull String semester;

  private int count;
}
