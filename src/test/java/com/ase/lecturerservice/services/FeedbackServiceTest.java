package com.ase.lecturerservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.repositories.FeedbackRepository;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

  @Mock
  private FeedbackRepository feedbackRepository;

  @Mock
  private ExamService examService;

  @InjectMocks
  private FeedbackService feedbackService;

  private String lecturerUuid;
  private Feedback feedback1;
  private Feedback feedback2;
  private Feedback feedback3;
  private Exam exam1;
  private Exam exam2;
  private Exam exam3;

  @BeforeEach
  void setUp() {
    lecturerUuid = "lecturer-123";

    exam1 = new Exam();
    exam1.setUuid("exam-1");
    exam1.setLecturerUuid(lecturerUuid);

    exam2 = new Exam();
    exam2.setUuid("exam-2");
    exam2.setLecturerUuid(lecturerUuid);

    exam3 = new Exam();
    exam3.setUuid("exam-3");
    exam3.setLecturerUuid("different-lecturer");

    feedback1 = new Feedback();
    feedback1.setExamUuid("exam-1");
    feedback1.setStudentUuid("student-1");

    feedback2 = new Feedback();
    feedback2.setExamUuid("exam-2");
    feedback2.setStudentUuid("student-2");

    feedback3 = new Feedback();
    feedback3.setExamUuid("exam-3");
    feedback3.setStudentUuid("student-3");
  }

  @Test
  void saveFeedbackShouldCallRepository() {
    Feedback feedback = Feedback.builder()
        .uuid("test-uuid")
        .comment("Test comment")
        .points(1)
        .grade(1.0f)
        .build();

    feedbackService.saveFeedback(feedback);

    verify(feedbackRepository).save(feedback);
  }

  @Test
  void saveFeedbackShouldHandleNullFeedback() {
    assertThrows(NullPointerException.class, () -> feedbackService.saveFeedback(null));
  }

  @Test
  void getFeedbackForLecturerShouldReturnFeedbackForCorrectLecturer() {
    List<Feedback> allFeedbacks = List.of(feedback1, feedback2, feedback3);
    when(feedbackRepository.findAll()).thenReturn(allFeedbacks);
    when(examService.getExamsByLecturer(lecturerUuid)).thenReturn(List.of(exam1, exam2));

    List<Feedback> result = feedbackService.getFeedbackForLecturer(lecturerUuid);

    assertEquals(2, result.size());
    assertTrue(result.contains(feedback1));
    assertTrue(result.contains(feedback2));
    assertFalse(result.contains(feedback3));

    verify(feedbackRepository).findAll();
    verify(examService).getExamsByLecturer(lecturerUuid);
  }

  @Test
  void getFeedbackForLecturerShouldReturnEmptyListWhenNoMatchingLecturer() {
    List<Feedback> allFeedbacks = List.of(feedback1, feedback2, feedback3);
    when(feedbackRepository.findAll()).thenReturn(allFeedbacks);
    when(examService.getExamsByLecturer(anyString())).thenReturn(Collections.emptyList());

    List<Feedback> result = feedbackService.getFeedbackForLecturer(lecturerUuid);

    assertTrue(result.isEmpty());
    verify(feedbackRepository).findAll();
  }
}
