package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.repositories.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {
  private final FeedbackRepository feedbackRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void instantiateDummies() {
    log.info("Creating Dummies...");
    DummyData.Feedbacks.forEach(this::saveFeedback);
  }

  public List<Feedback> getFeedbackForLecturer(String lecturerUuid) {
    List<Feedback> feedbacks = feedbackRepository.findAll();
    return feedbacks.stream().filter(feedback ->
        Optional.ofNullable(getExam(feedback.getExamUuid()))
            .map(exam -> exam.getLecturerUuid().equals(lecturerUuid))
            .orElse(false)
    ).toList();
  }

  public List<Feedback> getFeedbackForExam(String examUuid) {
    return feedbackRepository.findByExamUuid(examUuid);
  }

  // TODO: change this webclient, when the API Endpoint is ready
  public Exam getExam(String uuid) {
    return DummyData.EXAMS.stream()
        .filter(exam -> exam.getUuid().equals(uuid))
        .findFirst().orElse(null);
  }

  public void saveFeedback(Feedback feedback) {
    feedback.setUuid(null);
    feedbackRepository.save(feedback);
    log.info("Saving grade with UUID: {}", feedback.getUuid());
  }

  public void updateFeedback(String uuid, Feedback updateFeedback) {
    Feedback existing = feedbackRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback not found"));

    existing.setComment(updateFeedback.getComment());
    existing.setPoints(updateFeedback.getPoints());
    existing.setGrade(updateFeedback.getGrade());
    existing.setFileReference(updateFeedback.getFileReference());
    existing.setGradedAt(LocalDate.now());

    feedbackRepository.save(existing);

    log.info("Feedback {} was successfully edited by Lecturer {} "
            + "(Student {}, Points: {}, Grade: {})",
        uuid,
        existing.getLecturerUuid(),
        existing.getStudentUuid(),
        existing.getPoints(),
        existing.getGrade());
  }

}
