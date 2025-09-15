package com.ase.lecturerservice.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.user.Student;
import com.ase.lecturerservice.services.ExamService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExamController.class)
public class ExamControllerTest {
  private static final List<Student> STUDENT_LIST = List.of(
      Student.builder().uuid(
          MockValues.UuidMocks.STUDENT_UUID.getValue()).matriculationNumber("D725").build(),
      Student.builder().uuid(
          MockValues.UuidMocks.STUDENT_UUID2.getValue()).matriculationNumber("D755").build(),
      Student.builder().uuid(
          MockValues.UuidMocks.STUDENT_UUID3.getValue()).matriculationNumber("D735").build(),
      Student.builder().uuid(
          MockValues.UuidMocks.STUDENT_UUID4.getValue()).matriculationNumber("D729").build(),
      Student.builder().uuid(
          MockValues.UuidMocks.STUDENT_UUID5.getValue()).matriculationNumber("D726").build()
  );
  private static Exam exam;
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ExamService examService;

  @BeforeAll
  public static void setup() {
    LocalDate date = LocalDate.of(
        MockValues.IntMocks.DATE_YEAR.getValue(),
        MockValues.IntMocks.DATE_MONTH.getValue(),
        MockValues.IntMocks.DATE_DAY.getValue());

    exam = Exam.builder()
        .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .name("Test")
        .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
        .examType(ExamType.EXAM)
        .date(date)
        .time(MockValues.IntMocks.TIME_SECONDS.getValue())
        .allowedResources("Calculator, Formula Sheet")
        .attempt(MockValues.IntMocks.ATTEMPT.getValue())
        .etcs(MockValues.IntMocks.ETCS.getValue())
        .room("Room A101")
        .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
        .module("Test")
        .assignedStudents(STUDENT_LIST)
        .build();
  }

  @Test
  void fetchExamsShouldReturnExamDtos() throws Exception {
    when(examService.getExamsByLecturer("Tom")).thenReturn(List.of(exam));

    mockMvc.perform(get("/api/v1/exams?lecturerUuid={lecturerUuid}", "Tom")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].uuid")
            .value(MockValues.UuidMocks.EXAM_UUID.getValue()))
        .andExpect(jsonPath("$[0].name").value("Test"))
        .andExpect(jsonPath("$[0].date").value("2015-10-25"))
        .andExpect(jsonPath("$[0].module").value("Test"))
        .andExpect(jsonPath("$[0].time").value(MockValues.IntMocks.TIME_SECONDS.getValue()))
        .andExpect(jsonPath("$[0].examType").value(ExamType.EXAM.toString()))
        .andExpect(jsonPath("$[0].assignedStudents").isNotEmpty());
  }

  @Test
  void fetchExamsShouldThrowException() throws Exception {
    when(examService.getExamsByLecturer(" "))
        .thenThrow(new IllegalArgumentException("Lecturer cannot be empty"));

    mockMvc.perform(get("/api/v1/exams?lecturerUuid={lecturerUuid}", " ")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Lecturer cannot be empty"));
  }
}
