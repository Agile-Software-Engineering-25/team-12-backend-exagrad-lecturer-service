package com.ase.lecturerservice.dtos;

import com.ase.lecturerservice.entities.PublishStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentExamStateDto {
  private String studentUuid;
  private String examUuid;
  private PublishStatus publishStatus;
}
