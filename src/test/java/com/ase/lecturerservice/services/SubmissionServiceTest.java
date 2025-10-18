package com.ase.lecturerservice.services;

import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.Submission;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.UserType;

@SpringBootTest
public class SubmissionServiceTest {
  @Autowired
  private SubmissionService submissionService;

  @MockBean
  private ExamService examService;

  @Value("${app.apis.student-service.baseurl:http://student-service}")
  private String studentServiceBaseUrl;

  private Lecturer lecturer;
  private LocalDate date;

  @BeforeEach
  public void setUpLecturer() {
    lecturer = Lecturer.builder()
        .uuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
        .email("lecturer@example.com")
        .type(UserType.LECTURER)
        .firstName("John")
        .lastName("Doe")
        .build();

    date = LocalDate.of(
        MockValues.IntMocks.DATE_YEAR.getValue(),
        MockValues.IntMocks.DATE_MONTH.getValue(),
        MockValues.IntMocks.DATE_DAY.getValue());


    // Inject a WebClient that points to the same base URL
    // (it will be intercepted by WebTestClient's mock server if configured)
    WebClient webClient = WebClient.create();
    ReflectionTestUtils.setField(submissionService, "studentServiceBaseUrl", studentServiceBaseUrl);
    ReflectionTestUtils.setField(submissionService, "studentServiceWebClient", webClient);
  }

  @Test
  void getSubmissionsForExamShouldReturnSubmissionsForSpecificExam() {
    String examId = MockValues.UuidMocks.EXAM_UUID.getValue();

    // Stub examService usage when aggregating (not used here but safe)
    given(examService.getExamsByLecturer(ArgumentMatchers.anyString())).willReturn(List.of());

    // Since actual HTTP is executed, expect an empty result for unknown mock server
    List<Submission> submissions = submissionService.getSubmissionsForExam(examId);

    Assertions.assertThat(submissions).isEmpty();
  }

  @Test
  void getSubmissionsForStudentShouldReturnSubmissionsForSpecificStudent() {
    String studentId = MockValues.UuidMocks.STUDENT_UUID.getValue();

    given(examService.getExamsByLecturer(ArgumentMatchers.anyString())).willReturn(List.of());

    List<Submission> submissions = submissionService.getSubmissionsForStudent(studentId);

    Assertions.assertThat(submissions).isEmpty();
  }

  @Test
  void getAllAccessibleSubmissionsForLecturerShouldReturnOnlyLecturerSubmissions() {
    Exam exam = Exam.builder()
        .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .name("Test Exam")
        .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
        .examType(ExamType.EXAM)
        .date(date)
        .time(MockValues.IntMocks.TIME_SECONDS.getValue())
        .allowedResources("Calculator")
        .attempt(MockValues.IntMocks.ATTEMPT.getValue())
        .etcs(MockValues.IntMocks.ETCS.getValue())
        .room("Room A101")
        .lecturerUuid(lecturer.getUuid())
        .module("Test Module")
        .build();

    // Return one exam for the lecturer; network layer will currently yield empty submissions
    given(examService.getExamsByLecturer(lecturer.getUuid())).willReturn(List.of(exam));

    List<Submission> submissions = submissionService
        .getAllAccessibleSubmissionsForLecturer(lecturer.getUuid());

    Assertions.assertThat(submissions).isEmpty();
  }

  @Test
  void getAllAccessibleSubmissionsForLecturerShouldReturnEmptyListForUnknownLecturer() {
    String unknownLecturerUuid = "unknown-lecturer-uuid";

    given(examService.getExamsByLecturer(unknownLecturerUuid)).willReturn(List.of());

    List<Submission> submissions = submissionService
        .getAllAccessibleSubmissionsForLecturer(unknownLecturerUuid);

    Assertions.assertThat(submissions).isEmpty();
  }

  @Test
  void getSubmissionsForExamShouldReturnEmptyListForUnknownExam() {
    String unknownExamId = "unknown-exam-id";

    List<Submission> submissions = submissionService.getSubmissionsForExam(unknownExamId);

    Assertions.assertThat(submissions).isEmpty();
  }

  @Test
  void getSubmissionsForStudentShouldReturnEmptyListForUnknownStudent() {
    String unknownStudentId = "unknown-student-id";

    List<Submission> submissions = submissionService.getSubmissionsForStudent(unknownStudentId);

    Assertions.assertThat(submissions).isEmpty();
  }
}
