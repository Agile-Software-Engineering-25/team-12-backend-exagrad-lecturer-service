package com.ase.lecturerservice.dtos;

import java.util.List;
import java.util.UUID;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionDto {
  @NotNull
  private UUID uuid;

  @NotNull
  private List<UUID> studentUuid;

  @NotNull
  private int totalPoints;
}
