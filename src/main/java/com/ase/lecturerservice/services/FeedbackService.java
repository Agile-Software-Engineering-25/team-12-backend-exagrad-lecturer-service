package com.ase.lecturerservice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.ase.lecturerservice.entities.Exam;
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

  public List<Feedback> getFeedbackForLecturer(String lecturerUuid) {
    return DummyData.Feedbacks.stream().filter(feedback ->
        Optional.ofNullable(getExam(feedback.getExamUuid()))
            .map(value -> value.getLecturer().getUuid().equals(lecturerUuid))
            .orElse(false)
    ).toList();
  }

  public Exam getExam(String uuid) {
    return DummyData.EXAMS.stream().filter(exam -> exam.getUuid().equals(uuid)).findFirst().orElse(null);
  }
}
