package com.ase.userservice.controllers;

import com.ase.userservice.dtos.ExamDto;
import com.ase.userservice.entities.Exam;
import com.ase.userservice.services.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static com.ase.userservice.controllers.BaseController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH + "/lecturer")
@RequiredArgsConstructor
public class LecturerController {

  private final LecturerService lecturerService;

  @GetMapping("/exams")
  public ResponseEntity<List<ExamDto>> fetchExams(String lecturer) throws IllegalArgumentException{
    List<Exam> exams = lecturerService.fetchExamsByLecturer(lecturer);
    return ResponseEntity.ok(lecturerService.convertToExamDto(exams));
  }
}
