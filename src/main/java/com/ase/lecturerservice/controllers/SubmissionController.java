package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.entities.Submission;
import com.ase.lecturerservice.services.SubmissionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/submissions")
@RequiredArgsConstructor
public class SubmissionController {

  private final SubmissionService submissionService;

  @GetMapping("/for-lecturer/{lecturerUuid}")
  public ResponseEntity<List<Submission>> getRelevantSubmissions(@PathVariable String lecturerUuid) {
    return ResponseEntity.ok(submissionService.getAllAccessibleSubmissionsForLecturer(lecturerUuid));
  }
}
