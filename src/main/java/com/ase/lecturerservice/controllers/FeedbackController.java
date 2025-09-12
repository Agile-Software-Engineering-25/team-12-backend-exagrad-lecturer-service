package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.services.FeedbackService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/grades")
@RequiredArgsConstructor
public class FeedbackController {
  private final FeedbackService feedbackService;

  @GetMapping
  public ResponseEntity<Feedback> getFeedbackFromExam(
      @RequestParam String examUuid, @RequestParam String studentUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackExam(studentUuid, examUuid));
  }
}
