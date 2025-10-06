package com.ase.lecturerservice.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Embeddable
public class UploadFile {
  private String filename;
  private String contentType;

  @JsonProperty("data") // sagt Jackson: nimm das JSON-Feld "data"
  private byte[] content; // Jackson dekodiert automatisch Base64 → byte[]
}
