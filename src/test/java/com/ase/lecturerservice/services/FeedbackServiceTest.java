package com.ase.lecturerservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.mappers.FeedbackMapper;
import com.ase.lecturerservice.repositories.FeedbackRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {
    @Mock private FeedbackRepository feedbackRepository;
    @Mock private FeedbackMapper feedbackMapper;
    @Mock private FeedbackDocumentService feedbackDocumentService;

    @InjectMocks private FeedbackService feedbackService;

    private String lecturerUuid;
    private Feedback feedback1;
    private Feedback feedback2;
    private Feedback feedback3;
    private Exam exam1;
    private Exam exam2;
    private Exam exam3;

    private static final LocalDate DATE =
            LocalDate.of(
                    MockValues.IntMocks.DATE_YEAR.getValue(),
                    MockValues.IntMocks.DATE_MONTH.getValue(),
                    MockValues.IntMocks.DATE_DAY.getValue());

    @BeforeEach
    void setUp() {
        lecturerUuid = "lecturer-123";

        exam1 = new Exam();
        exam1.setUuid("exam-1");
        exam1.setLecturerUuid(lecturerUuid);

        exam2 = new Exam();
        exam2.setUuid("exam-2");
        exam2.setLecturerUuid(lecturerUuid);

        // Create test feedback
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

        ;
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

        FeedbackService spyService = spy(feedbackService);
        doReturn(exam1).when(spyService).getExam("exam-1");
        doReturn(exam2).when(spyService).getExam("exam-2");
        doReturn(exam3).when(spyService).getExam("exam-3");

        FeedbackResponse response1 = new FeedbackResponse();
        FeedbackResponse response2 = new FeedbackResponse();

        when(feedbackMapper.toResponse(eq(feedback1), any())).thenReturn(response1);

        when(feedbackMapper.toResponse(eq(feedback2), any())).thenReturn(response2);

        List<FeedbackResponse> result = spyService.getFeedbackForLecturer(lecturerUuid);
        System.out.println("Result size: " + result);

        List<FeedbackResponse> expectedResponses = List.of(response1, response2);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(expectedResponses));
        assertFalse(result.contains(feedback3));

        verify(feedbackRepository).findAll();
        verify(spyService).getExam("exam-1");
        verify(spyService).getExam("exam-2");
        verify(spyService).getExam("exam-3");
    }

    @Test
    void getFeedbackForLecturerShouldReturnEmptyListWhenNoMatchingLecturer() {
        List<Feedback> allFeedbacks = List.of(feedback1, feedback2, feedback3);
        when(feedbackRepository.findAll()).thenReturn(allFeedbacks);

        FeedbackService spyService = spy(feedbackService);
        doReturn(exam3).when(spyService).getExam("exam-1");
        doReturn(exam3).when(spyService).getExam("exam-2");
        doReturn(exam3).when(spyService).getExam("exam-3");

        List<FeedbackResponse> result = spyService.getFeedbackForLecturer(lecturerUuid);

        assertTrue(result.isEmpty());
        verify(feedbackRepository).findAll();
    }
}
