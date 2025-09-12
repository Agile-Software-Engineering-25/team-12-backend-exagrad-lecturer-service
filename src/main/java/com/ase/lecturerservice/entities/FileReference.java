package com.ase.lecturerservice.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Embeddable
public class FileReference {
  private String fileUuid;

  private String filename;

  private String downloadLink;

}
