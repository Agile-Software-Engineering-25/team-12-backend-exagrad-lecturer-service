package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.dtos.StudentExamStateDto;
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
  private final ExamService examService;
  private final BitfrostService bitfrostService;

  @EventListener(ApplicationReadyEvent.class)
  public void instantiateDummies() {
    log.info("Creating Dummies...");
    DummyData.Feedbacks.forEach(this::saveFeedback);
  }

  public List<Feedback> getFeedbackForLecturer(String lecturerUuid) {
    List<Feedback> feedbacks = feedbackRepository.findAll();
    List<Exam> exams = examService.getExamsByLecturer(lecturerUuid);

    Set<String> lecturerExamUuids = exams.stream()
        .map(Exam::getUuid)
        .collect(Collectors.toSet());

    return feedbacks.stream()
        .filter(feedback -> lecturerExamUuids.contains(feedback.getExamUuid()))
        .toList();
  }

  public List<Feedback> getFeedbackForExam(String examUuid) {
    return feedbackRepository.findByExamUuid(examUuid);
  }

  public List<Feedback> getFeedbackForStudent(String studentUuid) {
    return feedbackRepository.findByStudentUuid(studentUuid);
  }

  public Feedback saveFeedback(Feedback feedback) {
    feedback.setUuid(null);
    log.info("Saving grade with UUID: {}", feedback.getUuid());
    return feedbackRepository.save(feedback);
  }

  public Feedback updateFeedback(String uuid, Feedback updateFeedback) {
    Feedback existing = feedbackRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback not found"));

    existing.setComment(updateFeedback.getComment());
    existing.setPoints(updateFeedback.getPoints());
    existing.setGrade(updateFeedback.getGrade());
    existing.setFileReference(updateFeedback.getFileReference());
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

  public void updateFeedbackStatus(StudentExamStateDto studentExamStateDto) {
    List<Feedback> updatedFeebacks = feedbackRepository.findAll()
        .stream()
        .filter(feedback -> feedback.getStudentUuid().equals(studentExamStateDto.getStudentUuid())
            && feedback.getExamUuid().equals(studentExamStateDto.getExamUuid()))
        .peek(feedback -> feedback.setPublishStatus(studentExamStateDto.getPublishStatus()))
        .toList();

    feedbackRepository.saveAll(updatedFeebacks);
  }

}
