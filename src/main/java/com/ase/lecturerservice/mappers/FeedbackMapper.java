package com.ase.lecturerservice.mappers;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Feedback;

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
    response.setFileReference(fileReferences);

    return response;
  }

  public FeedbackResponse toResponse(Feedback feedback) {
    return toResponse(feedback, Collections.emptyList());
  }
}
