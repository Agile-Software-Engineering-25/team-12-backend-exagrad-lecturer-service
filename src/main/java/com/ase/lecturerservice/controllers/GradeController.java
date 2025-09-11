package com.ase.lecturerservice.controllers;

import com.ase.lecturerservice.entities.Grade;
import com.ase.lecturerservice.services.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH + "/grades")
public class GradeController {
  private final GradeService gradeService;

  public GradeController(GradeService gradeService) {
    this.gradeService = gradeService;
  }

  @GetMapping("/")
  public ResponseEntity<Grade> getGradeFromExam(@RequestParam String examUuid, @RequestParam String studentUuid) throws Exception {
    Grade grade = gradeService.getGradedExam(studentUuid, examUuid);
    if (grade == null) {
      throw new Exception();
    }

    return ResponseEntity.ok(gradeService.getGradedExam(studentUuid, examUuid));
  }
}
