package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.UserType;

@SpringBootTest
public class ExamServiceTest {
  @Autowired
  private ExamService examService;

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
  }

  @Test
  void fetchExamsByLecturerShouldGetExams() {
    DummyData.EXAMS = List.of(Exam.builder()
        .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .name("Mathematics Final Exam")
        .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
        .examType(ExamType.PRESENTATION)
        .date(date)
        .time(MockValues.IntMocks.TIME_SECONDS.getValue())
        .allowedResources("Calculator, Formula Sheet")
        .attempt(MockValues.IntMocks.ATTEMPT.getValue())
        .etcs(MockValues.IntMocks.ETCS.getValue())
        .room("Room A101")
        .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
        .module("Mathe")
        .build());

    List<Exam> exams = examService.getExamsByLecturer(lecturer.getUuid());
    Exam exam = exams.getFirst();

    Assertions.assertThat(exams).isNotEmpty();
    Assertions.assertThat(exam.getUuid())
        .isEqualTo(MockValues.UuidMocks.EXAM_UUID.getValue());
    Assertions.assertThat(exam.getName())
        .isEqualTo("Mathematics Final Exam");
    Assertions.assertThat(exam.getTotalPoints())
        .isEqualTo(MockValues.IntMocks.TOTAL_POINTS.getValue());
    Assertions.assertThat(exam.getExamType()).isEqualTo(ExamType.PRESENTATION);
    Assertions.assertThat(exam.getDate()).isEqualTo(date);
    Assertions.assertThat(exam.getTime())
        .isEqualTo(MockValues.IntMocks.TIME_SECONDS.getValue());
    Assertions.assertThat(exam.getAllowedResources())
        .isEqualTo("Calculator, Formula Sheet");
    Assertions.assertThat(exam.getAttempt())
        .isEqualTo(MockValues.IntMocks.ATTEMPT.getValue());
    Assertions.assertThat(exam.getEtcs())
        .isEqualTo(MockValues.IntMocks.ETCS.getValue());
    Assertions.assertThat(exam.getRoom()).isEqualTo("Room A101");
    Assertions.assertThat(exam.getLecturerUuid()).isEqualTo(lecturer.getUuid());
    Assertions.assertThat(exam.getModule()).isEqualTo("Mathe");
  }

  @Test
  void fetchExamsByLecturerShouldNotGetExams() {
    DummyData.EXAMS = List.of();

    List<Exam> exams = examService.getExamsByLecturer("Test");

    Assertions.assertThat(exams).isEmpty();
  }
}
