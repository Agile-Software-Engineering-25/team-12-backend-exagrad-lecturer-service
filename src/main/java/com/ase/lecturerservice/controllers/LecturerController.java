package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.dtos.SubmissionDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.services.LecturerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/lecturer")
@RequiredArgsConstructor
public class LecturerController {
  private final ObjectMapper objectMapper;

  private final LecturerService lecturerService;

  @GetMapping("/exams")
  public ResponseEntity<List<ExamDto>> getExams(@RequestParam String lecturer)
      throws IllegalArgumentException {
    List<Exam> exams = lecturerService.getExamsByLecturer(lecturer);

    List<ExamDto> examDtoList = exams.stream()
        .map(lecturerService::convertToExamDto)
        .collect(Collectors.toList());

    return ResponseEntity.ok(examDtoList);
  }

  @GetMapping("/exam/{examUuid}")
  public ResponseEntity<List<SubmissionDto>> getSubmissions(@PathVariable UUID examUuid) {
    List<Exam> exams = lecturerService.getExamWithoutSubmissions(examUuid);

    List<SubmissionDto> submissionDtoList = exams.stream()
        .map(exam -> objectMapper.convertValue(exam, SubmissionDto.class))
        .collect(Collectors.toList());
    return ResponseEntity.ok(submissionDtoList);
  }
}
