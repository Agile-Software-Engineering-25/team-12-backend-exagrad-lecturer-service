package com.ase.lecturerservice.controllers;

import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.services.LecturerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LecturerController.class)
public class LecturerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LecturerService lecturerService;

  private static List<ExamDto> examDtoList;

  @BeforeAll
  public static void setup() {
    examDtoList = List.of(ExamDto.builder()
      .name("Test")
      .module("Test")
      .date(LocalDate.of(2015, 10, 12))
      .time(5400)
      .build());
  }

  @Test
  void fetchExams_shouldReturnExamDtos() throws Exception {
    Mockito.when(lecturerService.getExamsByLecturer("john")).thenReturn(List.of());
    Mockito.when(lecturerService.convertToExamDto(List.of())).thenReturn(examDtoList);
    mockMvc.perform(get("/api/v1/lecturer/exams")
        .param("lecturer", "john")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].name").value("Test"))
      .andExpect(jsonPath("$[0].date").value("2015-10-12"))
      .andExpect(jsonPath("$[0].module").value("Test"))
      .andExpect(jsonPath("$[0].time").value(5400));
  }

  @Test
  void fetchExams_shouldThrowException() throws Exception {
    Mockito.when(lecturerService.getExamsByLecturer(""))
      .thenThrow(new IllegalArgumentException("Lecturer cannot be empty"));

    mockMvc.perform(get("/api/v1/lecturer/exams")
        .param("lecturer", "")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(content().string("Lecturer cannot be empty"));
  }
}
