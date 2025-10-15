package com.ase.lecturerservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {

    private String uuid;

    private LocalDate gradedAt;
    private String examUuid;
    private String lecturerUuid;
    private String studentUuid;
    private String submissionUuid;
    private String comment;
    private int points;
    private float grade;
    private List<FeedbackDocumentResponse> fileReference;
}