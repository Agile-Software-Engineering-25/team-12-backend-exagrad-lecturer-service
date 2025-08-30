package com.ase.lecturerservice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.services.LecturerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(LecturerController.class)
public class LecturerControllerTest {

  private static ExamDto examDto = ExamDto.builder()
      .uuid(UUID.randomUUID())
      .name("Test")
      .module("Test")
      .date(LocalDate.of(MockValues.DATE_YEAR.getValue(),
          MockValues.DATE_MONTH.getValue(),
          MockValues.DATE_DAY.getValue()))
      .time(MockValues.TIME_SECONDS.getValue())
      .build();

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private LecturerService lecturerService;

  @BeforeEach
  public void setup() {
    LocalDate date = LocalDate.of(
        MockValues.DATE_YEAR.getValue(),
        MockValues.DATE_MONTH.getValue(),
        MockValues.DATE_DAY.getValue());

    examDto = ExamDto.builder()
        .uuid(UUID.randomUUID())
        .name("Test")
        .module("Test")
        .date(date)
        .time(MockValues.TIME_SECONDS.getValue())
        .build();
  }

  @Test
  void fetchExamsShouldReturnExamDtos() throws Exception {
    Mockito.when(lecturerService.getExamsByLecturer("john")).thenReturn(List.of(new Exam()));
    Mockito.when(lecturerService.convertToExamDto(Mockito.any())).thenReturn(examDto);
    mockMvc.perform(get("/api/v1/lecturer/exams")
            .param("lecturer", "john")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Test"))
        .andExpect(jsonPath("$[0].date").value("2015-10-12"))
        .andExpect(jsonPath("$[0].module").value("Test"))
        .andExpect(jsonPath("$[0].time").value(MockValues.TIME_SECONDS.getValue()));
  }

  @Test
  void fetchExamsShouldThrowException() throws Exception {
    Mockito.when(lecturerService.getExamsByLecturer(""))
        .thenThrow(new IllegalArgumentException("Lecturer cannot be empty"));

    mockMvc.perform(get("/api/v1/lecturer/exams")
            .param("lecturer", "")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Lecturer cannot be empty"));
  }
}
