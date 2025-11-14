package com.ase.lecturerservice.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.entities.FileReference;

@Component
public class FeedbackMapper {

  public Feedback toEntity(FeedbackRequest feedbackRequest) {
    if (feedbackRequest == null) {
      return null;
    }

    Feedback entity = new Feedback();

    entity.setExamUuid(feedbackRequest.getExamUuid());
    entity.setLecturerUuid(feedbackRequest.getLecturerUuid());
    entity.setStudentUuid(feedbackRequest.getStudentUuid());
    entity.setSubmissionUuid(feedbackRequest.getSubmissionUuid());
    entity.setComment(feedbackRequest.getComment());
    entity.setGrade(feedbackRequest.getGrade());
    entity.setPoints(feedbackRequest.getPoints());
    entity.setPublishStatus(feedbackRequest.getPublishStatus());

    return entity;
  }

  public FeedbackResponse toResponse(
      Feedback feedback, List<FeedbackDocumentResponse> fileReferences) {
    if (feedback == null) {
      return null;
    }

    FeedbackResponse response = new FeedbackResponse();

    response.setUuid(feedback.getUuid());
    response.setGradedAt(feedback.getGradedAt());
    response.setExamUuid(feedback.getExamUuid());
    response.setLecturerUuid(feedback.getLecturerUuid());
    response.setStudentUuid(feedback.getStudentUuid());
    response.setSubmissionUuid(feedback.getSubmissionUuid());
    response.setComment(feedback.getComment());
    response.setPoints(feedback.getPoints());
    response.setGrade(feedback.getGrade());
    response.setPublishStatus(feedback.getPublishStatus());
    response.setFileReference(fileReferences);

    return response;
  }

  public FeedbackResponse toResponse(Feedback feedback) {
    if (feedback == null) {
      return null;
    }

    List<FeedbackDocumentResponse> fileReferences =
        feedback.getFileReferences() == null
            ? Collections.emptyList()
            : feedback.getFileReferences().stream()
            .map(this::mapFileReferenceToResponse)
            .collect(Collectors.toList());

    return toResponse(feedback, fileReferences);
  }

  private FeedbackDocumentResponse mapFileReferenceToResponse(FileReference fileReference) {
    FeedbackDocumentResponse response = new FeedbackDocumentResponse();
    response.setUuid(fileReference.getFileUuid());
    response.setFileName(fileReference.getFileName());
    return response;
  }
}
