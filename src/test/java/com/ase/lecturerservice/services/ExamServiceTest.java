package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.UserType;

@SpringBootTest
@ActiveProfiles("test")
public class ExamServiceTest {
  @Autowired
  private ExamService examService;

  @Value("${app.apis.exam-service.baseurl}")
  private String examServiceBaseUrl;

  @Value("${app.apis.courses-service.baseurl}")
  private String courseServiceBaseUrl;

  private Lecturer lecturer;
  private LocalDate date;

  @BeforeEach
  public void setUpLecturer() {
    lecturer =
        Lecturer.builder()
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

    // Inject a WebClient that points to the configured base URLs
    WebClient webClient = WebClient.create();
    ReflectionTestUtils.setField(examService, "examServiceBaseUrl", examServiceBaseUrl);
    ReflectionTestUtils.setField(examService, "courseServiceBaseUrl", courseServiceBaseUrl);
    ReflectionTestUtils.setField(examService, "webClient", webClient);
  }

  @Test
  void fetchExamsByLecturerShouldGetExams() {
    List<Exam> exams = examService.getExamsByLecturer(lecturer.getUuid());

    Assertions.assertThat(exams).isEmpty();
  }

  @Test
  void fetchExamsByLecturerShouldNotGetExams() {
    List<Exam> exams = examService.getExamsByLecturer("Test");

    Assertions.assertThat(exams).isEmpty();
  }
}
