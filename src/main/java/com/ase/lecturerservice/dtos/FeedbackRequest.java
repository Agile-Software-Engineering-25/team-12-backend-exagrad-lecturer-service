package com.ase.lecturerservice.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ein Data Transfer Object (DTO), das die Daten für die Erstellung eines neuen Feedbacks vom
 * Frontend zum Backend transportiert. Es enthält keine Datenbank-spezifischen Annotationen oder
 * Logik.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackRequest {

    private LocalDate gradedAt;
    private String examUuid;
    private String lecturerUuid;
    private String studentUuid;
    private String submissionUuid;
    private String comment;
    private int points;
    private float grade;
}
