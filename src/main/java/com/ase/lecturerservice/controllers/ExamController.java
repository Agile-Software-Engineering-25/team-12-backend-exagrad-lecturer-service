package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.services.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/exams")
@RequiredArgsConstructor
public class ExamController {
  private static final int SECONDS_PER_MINUTE = 60;
  private final ExamService examService;
  private final ObjectMapper objectMapper;

  @GetMapping("/{lecturerUuid}")
  public ResponseEntity<List<ExamDto>> getExams(@PathVariable String lecturerUuid)
      throws IllegalArgumentException {
    List<Exam> exams = examService.getExamsByLecturer(lecturerUuid);

    List<ExamDto> examDtoList = exams.stream()
        .map(exam -> objectMapper.convertValue(exam, ExamDto.class))
        .peek(exam -> exam.setTime(exam.getTime() / SECONDS_PER_MINUTE))
        .collect(Collectors.toList());

    return ResponseEntity.ok(examDtoList);
  }
}
