package com.ase.lecturerservice.services;

import org.springframework.stereotype.Service;
import com.ase.lecturerservice.entities.Feedback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {

  // TODO: Adjust this when saving data is implemented
  public Feedback getFeedbackExam(String studentUuid, String examUuid) {
    return DummyData.Feedbacks.stream()
        .filter(feedback -> feedback.getStudentUuid().equals(studentUuid)
            && feedback.getExamUuid().equals(examUuid))
        .findFirst()
        .orElse(null);
  }
}
