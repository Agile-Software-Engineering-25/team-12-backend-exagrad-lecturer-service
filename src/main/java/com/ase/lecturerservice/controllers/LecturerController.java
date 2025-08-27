package com.ase.lecturerservice.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.services.LecturerService;
import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/lecturer")
@RequiredArgsConstructor
public class LecturerController {

  private final LecturerService lecturerService;

  @GetMapping("/exams")
  public ResponseEntity<List<ExamDto>> getExams(
      @RequestParam String lecturer) throws IllegalArgumentException {
    List<Exam> exams = lecturerService.getExamsByLecturer(lecturer);

    List<ExamDto> examDtoList = exams.stream()
        .map(lecturerService::convertToExamDto)
        .collect(Collectors.groupingBy(ExamDto::getName))
        .values()
        .stream()
        .map(dtos -> {
          ExamDto merged = dtos.getFirst();
          merged.setSubmissionsCount(dtos.size());
          return merged;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(examDtoList);
  }
}
