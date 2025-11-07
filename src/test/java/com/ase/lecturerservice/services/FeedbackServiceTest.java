package com.ase.lecturerservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.dtos.StudentExamStateDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.mappers.FeedbackMapper;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.entities.PublishStatus;
import com.ase.lecturerservice.repositories.FeedbackRepository;



@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {
  private static final LocalDate DATE =
      LocalDate.of(
          MockValues.IntMocks.DATE_YEAR.getValue(),
          MockValues.IntMocks.DATE_MONTH.getValue(),
          MockValues.IntMocks.DATE_DAY.getValue());
  @Mock
  private FeedbackRepository feedbackRepository;
  @Mock
  private FeedbackMapper feedbackMapper;
  @Mock
  private FeedbackDocumentService feedbackDocumentService;
  @Mock
  private ExamService examService;

  @InjectMocks
  private FeedbackService feedbackService;
  private StudentExamStateDto dto;
  private StudentExamStateDto dto2;
  private StudentExamStateDto dto3;
  private String lecturerUuid;
  private Feedback feedback1;
  private Feedback feedback2;
  private Feedback feedback3;
  private FeedbackResponse response1;
  private FeedbackResponse response2;
  private FeedbackResponse response3;
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
    feedback1.setPublishStatus(PublishStatus.PUBLISHED);

    feedback2 = new Feedback();
    feedback2.setExamUuid("exam-2");
    feedback2.setStudentUuid("student-2");
    feedback2.setPublishStatus(PublishStatus.PUBLISHED);

    feedback3 = new Feedback();
    feedback3.setExamUuid("exam-3");
    feedback3.setStudentUuid("student-3");
    feedback3.setPublishStatus(PublishStatus.PUBLISHED);
    
    response1 = new FeedbackResponse();
    response1.setExamUuid("exam-1");
    response1.setStudentUuid("student-1");

    response2 = new FeedbackResponse();
    response2.setExamUuid("exam-2");
    response2.setStudentUuid("student-2");

    response3 = new FeedbackResponse();
    response3.setExamUuid("exam-3");
    response3.setStudentUuid("student-3");

    dto = new StudentExamStateDto();
    dto.setStudentUuid("student-1");
    dto.setExamUuid("exam-1");
    dto.setPublishStatus(PublishStatus.APPROVED);

    dto2 = new StudentExamStateDto();
    dto2.setStudentUuid("student-2");
    dto2.setExamUuid("exam-2");
    dto2.setPublishStatus(PublishStatus.REJECTED);

    dto3 = new StudentExamStateDto();
    dto3.setStudentUuid("Test");
    dto3.setExamUuid("Test");
    dto3.setPublishStatus(PublishStatus.PUBLISHED);
  }

  @Test
  void saveFeedbackShouldCallRepository() {

    Feedback feedback =
        Feedback.builder()
            .comment("Test comment")
            .points(1)
            .grade(1.0f)
            .gradedAt(DATE)
            .build();
    FeedbackRequest feedbackRequest =
        FeedbackRequest.builder()
            .comment("Test comment")
            .points(1)
            .grade(1.0f)
            .gradedAt(DATE)
            .build();
    when(feedbackMapper.toEntity(feedbackRequest)).thenReturn(feedback);
    when(feedbackRepository.save(feedback)).thenReturn(feedback);

    feedbackService.saveFeedback(feedbackRequest, new MultipartFile[0]);

    verify(feedbackRepository).save(feedback);
  }

  @Test
  void saveFeedbackShouldHandleNullFeedback() {
    assertThrows(
        NullPointerException.class,
        () -> {
          feedbackService.saveFeedback(null, new MultipartFile[0]);
        });
  }

  @Test
  void getFeedbackForLecturerShouldReturnFeedbackForCorrectLecturer() {
    List<Feedback> allFeedbacks = List.of(feedback1, feedback2, feedback3);
    when(feedbackRepository.findAll()).thenReturn(allFeedbacks);
    when(examService.getExamsByLecturer(lecturerUuid)).thenReturn(List.of(exam1, exam2));

    when(feedbackMapper.toResponse(eq(feedback1), any())).thenReturn(response1);
    when(feedbackMapper.toResponse(eq(feedback2), any())).thenReturn(response2);

    List<FeedbackResponse> result = feedbackService.getFeedbackForLecturer(lecturerUuid);

    assertEquals(2, result.size());
    assertTrue(result.contains(response1));
    assertTrue(result.contains(response2));
    assertFalse(result.contains(response3));

    verify(feedbackRepository).findAll();
    verify(examService).getExamsByLecturer(lecturerUuid);
  }


  @Test
  void getFeedbackForLecturerShouldReturnEmptyListWhenNoMatchingLecturer() {
    List<Feedback> allFeedbacks = List.of(feedback1, feedback2, feedback3);
    when(feedbackRepository.findAll()).thenReturn(allFeedbacks);
    when(examService.getExamsByLecturer(anyString())).thenReturn(Collections.emptyList());

    List<FeedbackResponse> result = feedbackService.getFeedbackForLecturer(lecturerUuid);

    assertTrue(result.isEmpty());
    verify(feedbackRepository).findAll();
  }

  @Test
  void shouldUpdateFeedbackStatusToApproved() {
    when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2));

    feedbackService.updateFeedbackStatus(dto);

    ArgumentCaptor<List<Feedback>> captor = ArgumentCaptor.forClass(List.class);
    verify(feedbackRepository).saveAll(captor.capture());

    List<Feedback> savedFeedbacks = captor.getValue();

    assertEquals(PublishStatus.APPROVED, savedFeedbacks.getFirst().getPublishStatus());
  }

  @Test
  void shouldUpdateFeedbackStatusToRejected() {
    when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2));

    feedbackService.updateFeedbackStatus(dto2);

    ArgumentCaptor<List<Feedback>> captor = ArgumentCaptor.forClass(List.class);
    verify(feedbackRepository).saveAll(captor.capture());

    List<Feedback> savedFeedbacks = captor.getValue();

    assertEquals(PublishStatus.REJECTED, savedFeedbacks.getFirst().getPublishStatus());
  }

  @Test
  void shouldNotUpdateFeedbackStatus() {
    when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2));

    feedbackService.updateFeedbackStatus(dto3);

    ArgumentCaptor<List<Feedback>> captor = ArgumentCaptor.forClass(List.class);
    verify(feedbackRepository).saveAll(captor.capture());

    List<Feedback> savedFeedbacks = captor.getValue();

    assertTrue(savedFeedbacks.isEmpty());
  }
}
