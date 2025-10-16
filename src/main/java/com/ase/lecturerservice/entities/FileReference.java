package com.ase.lecturerservice.entities;

import jakarta.persistence.Embeddable;
import java.util.UUID;
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
    private UUID fileUuid;
    private String fileName;
}
