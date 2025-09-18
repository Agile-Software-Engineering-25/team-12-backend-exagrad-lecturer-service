package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  void getSubmissionsForExamShouldReturnSubmissionsForSpecificExam() {
    String examId = MockValues.UuidMocks.EXAM_UUID.getValue();

    List<Submission> submissions = submissionService.getSubmissionsForExam(examId);

    Assertions.assertThat(submissions).isNotEmpty();
    Assertions.assertThat(submissions)
        .allMatch(submission -> submission.getExamUuid().equals(examId));
  }

  @Test
  void getSubmissionsForStudentShouldReturnSubmissionsForSpecificStudent() {
    String studentId = MockValues.UuidMocks.STUDENT_UUID.getValue();

    List<Submission> submissions = submissionService.getSubmissionsForStudent(studentId);

    Assertions.assertThat(submissions).isNotEmpty();
    Assertions.assertThat(submissions)
        .allMatch(submission -> submission.getStudentUuid().equals(studentId));
  }

  @Test
  void getAllAccessibleSubmissionsForLecturerShouldReturnOnlyLecturerSubmissions() {
    // Setup exam for lecturer
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

    DummyData.EXAMS = List.of(exam);

    List<Submission> submissions = submissionService
        .getAllAccessibleSubmissionsForLecturer(lecturer.getUuid());

    Assertions.assertThat(submissions).isNotEmpty();
    // All submissions should be for exams belonging to this lecturer
    Assertions.assertThat(submissions).allMatch(submission ->
        DummyData.EXAMS.stream()
            .anyMatch(ex -> ex.getUuid().equals(submission.getExamUuid())
                && ex.getLecturerUuid().equals(lecturer.getUuid()))
    );
  }

  @Test
  void getAllAccessibleSubmissionsForLecturerShouldReturnEmptyListForUnknownLecturer() {
    String unknownLecturerUuid = "unknown-lecturer-uuid";

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
