package com.ase.lecturerservice.entities;

import java.util.UUID;
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
  protected UUID fileUuid;

  protected String filename;

  protected String downloadLink;

}
