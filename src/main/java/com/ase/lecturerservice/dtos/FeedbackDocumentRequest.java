package com.ase.lecturerservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackDocumentRequest {
    private String feedbackId;
    private String lecturerId;
}
