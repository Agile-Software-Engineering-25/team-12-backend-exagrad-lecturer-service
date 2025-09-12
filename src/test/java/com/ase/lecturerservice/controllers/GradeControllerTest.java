package com.ase.lecturerservice.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.UUID;
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
import com.ase.lecturerservice.entities.Grade;
import com.ase.lecturerservice.services.GradeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GradeController.class)
public class GradeControllerTest {
  private static Grade grade;

  private static final LocalDate DATE = LocalDate.of(
      MockValues.IntMocks.DATE_YEAR.getValue(),
      MockValues.IntMocks.DATE_MONTH.getValue(),
      MockValues.IntMocks.DATE_DAY.getValue());

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private GradeService gradeService;

  @BeforeAll
  public static void setup() {
    grade = Grade.builder()
        .uuid(MockValues.UuidMocks.GRADE_UUID.getValue())
        .gradedAt(DATE)
        .lecturerUuid(UUID.randomUUID().toString())
        .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
        .submissionUuid(UUID.randomUUID().toString())
        .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
        .comment("Excellent work on the assignment.")
        .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
        .grade(MockValues.FloatMocks.GRADE.getValue())
        .build();
  }

  @Test
  void getGradedExamShouldReturnGradeWhenValidUuids() throws Exception {
    String studentUuid = MockValues.UuidMocks.STUDENT_UUID.getValue();
    String examUuid = MockValues.UuidMocks.EXAM_UUID.getValue();

    when(gradeService.getGradedExam(studentUuid, examUuid))
        .thenReturn(grade);

    mockMvc.perform(get("/api/v1/grades/")
            .param("studentUuid", studentUuid)
            .param("examUuid", examUuid)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.gradedAt").value(DATE.toString()))
        .andExpect(jsonPath("$.studentUuid").value(studentUuid))
        .andExpect(jsonPath("$.examUuid").value(examUuid))
        .andExpect(jsonPath("$.comment").value("Excellent work on the assignment."))
        .andExpect(jsonPath("$.grade").value(grade.getGrade()))
        .andExpect(jsonPath("$.points").value(grade.getPoints()));
  }
}
