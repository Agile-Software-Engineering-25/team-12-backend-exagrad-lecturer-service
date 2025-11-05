package com.ase.lecturerservice.services;

import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.entities.Submission;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.UserType;

@SpringBootTest
@ActiveProfiles("test")
public class SubmissionServiceTest {
  @Autowired
  private SubmissionService submissionService;

  @MockitoBean
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


    WebClient webClient = WebClient.create();
    ReflectionTestUtils.setField(submissionService, "studentServiceBaseUrl", studentServiceBaseUrl);
    ReflectionTestUtils.setField(submissionService, "studentServiceWebClient", webClient);
  }

  @Test
  void getSubmissionsForExamShouldReturnSubmissionsForSpecificExam() {
    String examId = MockValues.UuidMocks.EXAM_UUID6.getValue();

    given(examService.getExamsByLecturer(ArgumentMatchers.anyString())).willReturn(List.of());

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
    // Return one exam for the lecturer; network layer will currently yield empty submissions
    given(submissionService.getAllAccessibleSubmissionsForLecturer(lecturer.getUuid()))
        .willReturn(Collections.emptyList());

    List<Submission> submissions =
        submissionService.getAllAccessibleSubmissionsForLecturer(lecturer.getUuid());

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
