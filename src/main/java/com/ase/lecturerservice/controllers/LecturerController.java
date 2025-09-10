package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.dtos.GradeDto;
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

  @GetMapping("/exam/{examUuid}/data")
  public ResponseEntity<SubmissionDto> getExamData(@PathVariable UUID examUuid) throws Exception {
    Exam exam = lecturerService.getExamData(examUuid);

    List<GradeDto> gradeList = exam.getAssignedStudents().stream()
        .map(student -> lecturerService.getGradedExam(student.getId(), examUuid))
        .filter(Objects::nonNull)
        .map(grade -> objectMapper.convertValue(grade, GradeDto.class))
        .collect(Collectors.toList());

    SubmissionDto submissionDto = SubmissionDto.builder()
        .totalPoints(exam.getTotalPoints())
        .student(exam.getAssignedStudents())
        .grade(gradeList)
        .build();

    return ResponseEntity.ok(submissionDto);
  }
}
