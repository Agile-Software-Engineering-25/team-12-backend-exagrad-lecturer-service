package com.ase.lecturerservice.mappers;

import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Feedback;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Mapper-Komponente, die für die Umwandlung zwischen Feedback-DTOs und Feedback-Datenbank-Entitäten
 * zuständig ist.
 */
@Component
public class FeedbackMapper {

    /**
     * Wandelt ein FeedbackCreateRequest DTO in ein Feedback Entity um. Diese Methode wird
     * verwendet, bevor das Feedback in der Datenbank gespeichert wird.
     *
     * @param dto Das aus dem Frontend empfangene DTO.
     * @return Ein neues Feedback-Entity, das bereit zum Speichern ist.
     */
    public Feedback toEntity(FeedbackRequest feedbackRequest) {
        if (feedbackRequest == null) {
            return null;
        }

        Feedback entity = new Feedback();

        // Kopiere alle relevanten Felder vom feedbackRequest zum Entity
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
}
