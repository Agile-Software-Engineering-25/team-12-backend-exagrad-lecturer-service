package com.ase.lecturerservice.services;

import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LecturerServiceTest {
  @Autowired
  private LecturerService lecturerService;

  private Lecturer lecturer;

  @BeforeEach
  public void setUpLecturer() {
    lecturer = Lecturer.builder()
        .id(UUID.randomUUID())
        .email("lecturer@example.com")
        .type(UserType.LECTURER)
        .firstName("John")
        .lastName("Doe")
        .build();
  }
  @Test
  void fetchExamsByLecturer_shouldGetExams() {
    DummyData.EXAMS = List.of(Exam.builder()
        .name("Mathematics Final Exam")
        .grade(1)
        .averageGrade(2)
        .totalPoints(100)
        .achievedPoints(95)
        .examType("Written")
        .date(LocalDate.of(2015, 10, 12))
        .time(5400)
        .allowedResources("Calculator, Formula Sheet")
        .attempt(1)
        .etcs(5)
        .room("Room A101")
        .lecturer(lecturer)
        .module("Mathe")
        .build());

    List<Exam> exams = lecturerService.getExamsByLecturer("Test");
    Exam exam = exams.getFirst();

    Assertions.assertThat(exams).isNotEmpty();
    Assertions.assertThat(exam.getName()).isEqualTo("Mathematics Final Exam");
    Assertions.assertThat(exam.getGrade()).isEqualTo(1);
    Assertions.assertThat(exam.getAverageGrade()).isEqualTo(2);
    Assertions.assertThat(exam.getTotalPoints()).isEqualTo(100);
    Assertions.assertThat(exam.getAchievedPoints()).isEqualTo(95);
    Assertions.assertThat(exam.getExamType()).isEqualTo("Written");
    Assertions.assertThat(exam.getDate()).isEqualTo(LocalDate.of(2015, 10, 12));
    Assertions.assertThat(exam.getTime()).isEqualTo(5400);
    Assertions.assertThat(exam.getAllowedResources()).isEqualTo("Calculator, Formula Sheet");
    Assertions.assertThat(exam.getAttempt()).isEqualTo(1);
    Assertions.assertThat(exam.getEtcs()).isEqualTo(5);
    Assertions.assertThat(exam.getRoom()).isEqualTo("Room A101");
    Assertions.assertThat(exam.getLecturer()).isEqualTo(lecturer);
    Assertions.assertThat(exam.getModule()).isEqualTo("Mathe");
  }

  @Test
  void fetchExamsByLecturer_shouldNotGetExams() {
    DummyData.EXAMS = List.of();

    List<Exam> exams = lecturerService.getExamsByLecturer("Test");

    Assertions.assertThat(exams).isEmpty();
  }

  @Test
  void convertToExamDto_shouldConvertExamsToDto() {
    List<Exam> exams = List.of(Exam.builder()
        .name("Mathematics Final Exam")
        .grade(1)
        .averageGrade(2)
        .totalPoints(100)
        .achievedPoints(95)
        .examType("Written")
        .date(LocalDate.of(2015, 10, 12))
        .time(5400)
        .allowedResources("Calculator, Formula Sheet")
        .attempt(1)
        .etcs(5)
        .room("Room A101")
        .lecturer(lecturer)
        .module("Mathe")
        .build());

    List<ExamDto> examDtos = lecturerService.convertToExamDto(exams);
    ExamDto examDto = examDtos.getFirst();

    Assertions.assertThat(examDtos).isNotEmpty();
    Assertions.assertThat(examDto.getName()).isEqualTo("Mathematics Final Exam");
    Assertions.assertThat(examDto.getModule()).isEqualTo("Mathe");
    Assertions.assertThat(examDto.getDate()).isEqualTo(LocalDate.of(2015, 10, 12));
    Assertions.assertThat(examDto.getTime()).isEqualTo(90);
    Assertions.assertThat(examDto.getSubmissions()).isEqualTo(1);
  }

  @Test
  void convertToExamDto_shouldGetEmptyList() {
    List<Exam> exams = List.of();

    List<ExamDto> examDtos = lecturerService.convertToExamDto(exams);

    Assertions.assertThat(examDtos).isEmpty();
  }
}
