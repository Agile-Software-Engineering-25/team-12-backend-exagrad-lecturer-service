package com.ase.lecturerservice.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.dtos.FeedbackDocumentRequest;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.entities.FeedbackDocument;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.mappers.FeedbackDocumentMapper;
import com.ase.lecturerservice.mappers.FeedbackMapper;
import com.ase.lecturerservice.repositories.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {
  private final FeedbackRepository feedbackRepository;
  private final FeedbackDocumentService feedbackDocumentService;
  private final FeedbackMapper feedbackMapper;
  private final FeedbackDocumentMapper feedbackDocumentMapper;
  private final ExamService examService;
  private final BitfrostService bitfrostService;

  @EventListener(ApplicationReadyEvent.class)
  public void instantiateDummies() {
    log.info("Creating Dummies...");
    DummyData.Feedbacks.forEach(
        feedbackRequest -> {
          this.saveFeedback(feedbackRequest, new MultipartFile[0]);
        });
  }

  public List<FeedbackResponse> getFeedbackForLecturer(String lecturerUuid) {
    List<Feedback> feedbacks = feedbackRepository.findAll();
    List<Exam> exams = examService.getExamsByLecturer(lecturerUuid);

    Set<String> lecturerExamUuids = exams.stream()
        .map(Exam::getUuid)
        .collect(Collectors.toSet());

    return feedbacks.stream()
        .filter(feedback -> lecturerExamUuids.contains(feedback.getExamUuid()))
        .map(
            feedback -> {
              return feedbackMapper.toResponse(
                  feedback,
                  feedbackDocumentService.getDocumentsByFeedbackId(
                      feedback.getUuid()));
            })
        .toList();
  }

  public List<Feedback> getFeedbackForStudent(String studentUuid) {
    return feedbackRepository.findByStudentUuid(studentUuid);
  }

  public void saveFeedback(FeedbackRequest feedback, MultipartFile[] files) {
    Feedback feedbackEntity = feedbackMapper.toEntity(feedback);
    feedbackEntity.setGradedAt(LocalDate.now());
    Feedback savedFeedback = feedbackRepository.save(feedbackEntity);
    
    log.info("Saving grade with UUID: {} for lecturer: {}",
        savedFeedback.getUuid(), feedback.getLecturerUuid());

    List<FileReference> savedDocuments = new ArrayList<>();
    if (files != null && files.length > 0) {
      log.info("number of files: {}", files.length);
      for (MultipartFile file : files) {
        try {
          FeedbackDocumentRequest metadata =
              new FeedbackDocumentRequest(
                  savedFeedback.getUuid(), feedback.getLecturerUuid());
          FeedbackDocument savedDocument =
              feedbackDocumentService.uploadFeedbackDocument(file, metadata);
          savedDocuments.add(feedbackDocumentMapper.toReference(savedDocument));

        } 
        catch (IOException e) {
          log.error("Failed to upload file for feedback {}", savedFeedback.getUuid(), e);
          throw new RuntimeException("File upload failed.", e);
        }
      }
      log.info(
          "Uploaded {} files associated with feedback UUID: {}",
          files.length,
          savedFeedback.getUuid());
      savedFeedback.setFileReferences(savedDocuments);
    }
  }

  public Feedback updateFeedback(String uuid, Feedback updateFeedback) {
    Feedback existing = feedbackRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback not found"));

    existing.setComment(updateFeedback.getComment());
    existing.setPoints(updateFeedback.getPoints());
    existing.setGrade(updateFeedback.getGrade());
   // existing.setFileReference(updateFeedback.getFileReferences());
    existing.setGradedAt(LocalDate.now());

    log.info("Feedback {} was successfully edited by Lecturer {} "
            + "(Student {}, Points: {}, Grade: {})",
        uuid,
        existing.getLecturerUuid(),
        existing.getStudentUuid(),
        existing.getPoints(),
        existing.getGrade());

    return feedbackRepository.save(existing);
  }

  public void submitFeedback(List<Feedback> feedbacks) {
    log.info("submitting feedbacks to the examination office");
    bitfrostService.sendRequest("feedbacks:submit", feedbacks);
  }

}
