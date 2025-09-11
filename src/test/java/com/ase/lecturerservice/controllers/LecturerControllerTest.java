package com.ase.lecturerservice.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.Grade;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.Student;
import com.ase.lecturerservice.services.LecturerService;

@WebMvcTest(LecturerController.class)
public class LecturerControllerTest {
  private static ExamDto examDto;
  private static Grade grade;
  private static Exam exam;

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private LecturerService lecturerService;

  @BeforeAll
  public static void setup() {
    LocalDate date = LocalDate.of(
        MockValues.IntMocks.DATE_YEAR.getValue(),
        MockValues.IntMocks.DATE_MONTH.getValue(),
        MockValues.IntMocks.DATE_DAY.getValue());

    examDto = ExamDto.builder()
        .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .name("Test")
        .module("Test")
        .date(date)
        .time(MockValues.IntMocks.TIME_MIN.getValue())
        .examType(ExamType.TEST)
        .build();

    grade = Grade.builder()
        .uuid(MockValues.UuidMocks.GRADE_UUID.getValue())
        .date(date)
        .lecturerUuid(UUID.randomUUID())
        .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
        .submissionUuid(UUID.randomUUID())
        .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .comment("Excellent work on the assignment.")
        .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
        .grade(MockValues.FloatMocks.GRADE.getValue())
        .build();

    exam = Exam.builder()
        .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .name("Mathematics Final Exam")
        .grade(MockValues.FloatMocks.GRADE.getValue())
        .averageGrade(MockValues.FloatMocks.AVERAGE_GRADE.getValue())
        .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
        .achievedPoints(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
        .examType(ExamType.PRESENTATION)
        .date(date)
        .time(MockValues.IntMocks.TIME_SECONDS.getValue())
        .allowedResources("Calculator, Formula Sheet")
        .attempt(MockValues.IntMocks.ATTEMPT.getValue())
        .etcs(MockValues.IntMocks.ETCS.getValue())
        .room("Room A101")
        .lecturer(new Lecturer())
        .module("Mathe")
        .build();
  }

  @Test
  void fetchExamsShouldReturnExamDtos() throws Exception {
    Exam exam = new Exam();

    when(lecturerService.getExamsByLecturer("john")).thenReturn(List.of(exam));
    when(lecturerService.convertToExamDto(exam)).thenReturn(examDto);

    mockMvc.perform(get("/api/v1/lecturer/exams")
            .param("lecturer", "john")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].uuid")
            .value(MockValues.UuidMocks.EXAM_UUID.getValue().toString()))
        .andExpect(jsonPath("$[0].name").value("Test"))
        .andExpect(jsonPath("$[0].date").value("2015-10-12"))
        .andExpect(jsonPath("$[0].module").value("Test"))
        .andExpect(jsonPath("$[0].time").value(MockValues.IntMocks.TIME_MIN.getValue()))
        .andExpect(jsonPath("$[0].examType").value(ExamType.TEST.toString()));
  }

  @Test
  void fetchExamsShouldThrowException() throws Exception {
    when(lecturerService.getExamsByLecturer(""))
        .thenThrow(new IllegalArgumentException("Lecturer cannot be empty"));

    mockMvc.perform(get("/api/v1/lecturer/exams")
            .param("lecturer", "")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Lecturer cannot be empty"));
  }

  @Test
  void fetchExamDataShouldReturnSubmissionData() throws Exception {
    UUID examUuid = MockValues.UuidMocks.EXAM_UUID.getValue();
    Student student = Student.builder()
        .id(MockValues.UuidMocks.STUDENT_UUID.getValue())
        .build();
    exam.setAssignedStudents(List.of(student));

    when(lecturerService.getExamData(examUuid))
        .thenReturn(exam);
    when(lecturerService.getGradedExam(student.getId(), examUuid))
        .thenReturn(grade);

    mockMvc.perform(get("/api/v1/lecturer/exam/{examUuid}/data",
            examUuid))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPoints").value(exam.getTotalPoints()))
        .andExpect(jsonPath("$.student", hasSize(1)))
        .andExpect(jsonPath("$.student[0].id").value(student.getId().toString()))
        .andExpect(jsonPath("$.grade", hasSize(1)))
        .andExpect(jsonPath("$.grade[0].grade").value(grade.getGrade()));
  }

  @Test
  void fetchExamDataShouldReturnException() throws Exception {
    UUID examUuid = MockValues.UuidMocks.EXAM_UUID.getValue();

    when(lecturerService.getExamData(examUuid))
        .thenThrow(new Exception());

    mockMvc.perform(get("/api/v1/lecturer/exam/{examUuid}/data",
        examUuid))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string("Internal Server Error"))
    ;
  }

  @Test
  void fetchExamDataShouldFilterOutNullGrades() throws Exception {
    UUID examUuid = MockValues.UuidMocks.EXAM_UUID.getValue();
    Student student1 = Student.builder().id(UUID.randomUUID()).build();
    Student student2 = Student.builder().id(UUID.randomUUID()).build();
    exam.setAssignedStudents(List.of(student1, student2));

    when(lecturerService.getExamData(examUuid)).thenReturn(exam);
    when(lecturerService.getGradedExam(student1.getId(), examUuid)).thenReturn(grade);
    when(lecturerService.getGradedExam(student2.getId(), examUuid)).thenReturn(null);

    mockMvc.perform(get("/api/v1/lecturer/exam/{examUuid}/data", examUuid))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.grade", hasSize(1)));
  }

  @Test
  void fetchExamDataWithNoAssignedStudents() throws Exception {
    UUID examUuid = MockValues.UuidMocks.EXAM_UUID.getValue();
    exam.setAssignedStudents(Collections.emptyList());

    when(lecturerService.getExamData(examUuid)).thenReturn(exam);

    mockMvc.perform(get("/api/v1/lecturer/exam/{examUuid}/data", examUuid))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student", hasSize(0)))
        .andExpect(jsonPath("$.grade", hasSize(0)));
  }
}
