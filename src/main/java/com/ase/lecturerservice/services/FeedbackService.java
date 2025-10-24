package com.ase.lecturerservice.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    return feedbacks.stream()
        .filter(
            feedback ->
                Optional.ofNullable(getExam(feedback.getExamUuid()))
                    .map(exam -> exam.getLecturerUuid().equals(lecturerUuid))
                    .orElse(false))
        .map(
            feedback -> {
              log.info("Processing feedback UUID: {}", feedback.getUuid());
              log.info(
                  "feedback documents: {}",
                  feedback.getFileReferences());
              return feedbackMapper.toResponse(
                  feedback,
                  feedbackDocumentService.getDocumentsByFeedbackId(
                      feedback.getUuid()));
            })
        /* feedback.getFileReferences().stream().
        flatMap(ref ->
        {log.info("Fetching documents for file reference ID: {}", ref.getId());
        return feedbackDocumentService.getDocumentsByDocumentId(ref.getId()).stream();}).toList());}) */
        .toList();
  }

  // TODO: change this webclient, when the API Endpoint is ready
  public Exam getExam(String uuid) {
    return DummyData.EXAMS.stream()
        .filter(exam -> exam.getUuid().equals(uuid))
        .findFirst()
        .orElse(null);
  }

  public void saveFeedback(FeedbackRequest feedback, MultipartFile[] files) {
    Feedback feedbackEntity = feedbackMapper.toEntity(feedback);
    feedbackEntity.setGradedAt(LocalDate.now());
    Feedback savedFeedback = feedbackRepository.save(feedbackEntity);
    log.info("Saving grade with UUID: {}", savedFeedback.getUuid());

    List<FileReference> savedDocuments = new ArrayList<>();
    if (files != null && files.length > 0) {
      for (MultipartFile file : files) {
        try {
          FeedbackDocumentRequest metadata =
              new FeedbackDocumentRequest(
                  savedFeedback.getUuid(), feedback.getLecturerUuid());
          FeedbackDocument savedDocument =
              feedbackDocumentService.uploadFeedbackDocument(file, metadata);
          savedDocuments.add(feedbackDocumentMapper.toReference(savedDocument));

        } catch (IOException e) {
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
}
