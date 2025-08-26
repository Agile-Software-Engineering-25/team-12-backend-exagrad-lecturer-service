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
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.UserType;

@SpringBootTest
public class LecturerServiceTest {
  @Autowired
  private LecturerService lecturerService;

  private Lecturer lecturer;
  private LocalDate date;

  @BeforeEach
  public void setUpLecturer() {
    lecturer = Lecturer.builder()
        .id(UUID.randomUUID())
        .email("lecturer@example.com")
        .type(UserType.LECTURER)
        .firstName("John")
        .lastName("Doe")
        .build();

    date = LocalDate.of(
        MockValues.DATE_YEAR.getValue(),
        MockValues.DATE_MONTH.getValue(),
        MockValues.DATE_DAY.getValue());
  }

  @Test
  void fetchExamsByLecturerShouldGetExams() {
    DummyData.EXAMS = List.of(Exam.builder()
        .name("Mathematics Final Exam")
        .grade(MockValues.GRADE.getValue())
        .averageGrade(MockValues.AVERAGE_GRADE.getValue())
        .totalPoints(MockValues.TOTAL_POINTS.getValue())
        .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
        .examType("Written")
        .date(date)
        .time(MockValues.TIME_SECONDS.getValue())
        .allowedResources("Calculator, Formula Sheet")
        .attempt(MockValues.ATTEMPT.getValue())
        .etcs(MockValues.ETCS.getValue())
        .room("Room A101")
        .lecturer(lecturer)
        .module("Mathe")
        .build());

    List<Exam> exams = lecturerService.getExamsByLecturer("Test");
    Exam exam = exams.getFirst();

    Assertions.assertThat(exams).isNotEmpty();
    Assertions.assertThat(exam.getName())
        .isEqualTo("Mathematics Final Exam");
    Assertions.assertThat(exam.getGrade())
        .isEqualTo(MockValues.GRADE.getValue());
    Assertions.assertThat(exam.getAverageGrade())
        .isEqualTo(MockValues.AVERAGE_GRADE.getValue());
    Assertions.assertThat(exam.getTotalPoints())
        .isEqualTo(MockValues.TOTAL_POINTS.getValue());
    Assertions.assertThat(exam.getAchievedPoints())
        .isEqualTo(MockValues.ACHIEVED_POINTS.getValue());
    Assertions.assertThat(exam.getExamType()).isEqualTo("Written");
    Assertions.assertThat(exam.getDate()).isEqualTo(date);
    Assertions.assertThat(exam.getTime())
        .isEqualTo(MockValues.TIME_SECONDS.getValue());
    Assertions.assertThat(exam.getAllowedResources())
        .isEqualTo("Calculator, Formula Sheet");
    Assertions.assertThat(exam.getAttempt())
        .isEqualTo(MockValues.ATTEMPT.getValue());
    Assertions.assertThat(exam.getEtcs())
        .isEqualTo(MockValues.ETCS.getValue());
    Assertions.assertThat(exam.getRoom()).isEqualTo("Room A101");
    Assertions.assertThat(exam.getLecturer()).isEqualTo(lecturer);
    Assertions.assertThat(exam.getModule()).isEqualTo("Mathe");
  }

  @Test
  void fetchExamsByLecturerShouldNotGetExams() {
    DummyData.EXAMS = List.of();

    List<Exam> exams = lecturerService.getExamsByLecturer("Test");

    Assertions.assertThat(exams).isEmpty();
  }

  @Test
  void convertToExamDtoShouldConvertExamsToDto() {
    List<Exam> exams = List.of(Exam.builder()
        .name("Mathematics Final Exam")
        .grade(MockValues.GRADE.getValue())
        .averageGrade(MockValues.AVERAGE_GRADE.getValue())
        .totalPoints(MockValues.TOTAL_POINTS.getValue())
        .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
        .examType("Written")
        .date(date)
        .time(MockValues.TIME_SECONDS.getValue())
        .allowedResources("Calculator, Formula Sheet")
        .attempt(MockValues.ATTEMPT.getValue())
        .etcs(MockValues.ETCS.getValue())
        .room("Room A101")
        .lecturer(lecturer)
        .module("Mathe")
        .build());

    List<ExamDto> examDtos = lecturerService.convertToExamDto(exams);
    ExamDto examDto = examDtos.getFirst();

    Assertions.assertThat(examDtos).isNotEmpty();
    Assertions.assertThat(examDto.getName()).
        isEqualTo("Mathematics Final Exam");
    Assertions.assertThat(examDto.getModule()).
        isEqualTo("Mathe");
    Assertions.assertThat(examDto.getDate()).
        isEqualTo(date);
    Assertions.assertThat(examDto.getTime()).
        isEqualTo(MockValues.TIME_MIN.getValue());
    Assertions.assertThat(examDto.getSubmissions()).
        isEqualTo(MockValues.SUBMISSIONS.getValue());
  }

  @Test
  void convertToExamDtoShouldGetEmptyList() {
    List<Exam> exams = List.of();

    List<ExamDto> examDtos = lecturerService.convertToExamDto(exams);

    Assertions.assertThat(examDtos).isEmpty();
  }
}
