package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.services.FeedbackService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/feedback")
@RequiredArgsConstructor
public class FeedbackController {
  private final FeedbackService feedbackService;

  @GetMapping
  public ResponseEntity<Feedback> getFeedbackFromExam(
      @RequestParam String examUuid, @RequestParam String studentUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackExam(studentUuid, examUuid));
  }

  @GetMapping("/for-lecturer/{lecturerUuid}")
  public ResponseEntity<List<Feedback>> getFeedbacksForLecturer(@PathVariable String lecturerUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackForLecturer(lecturerUuid));
  }

  @PostMapping("/save")
  public ResponseEntity<Void> saveFeedback(@RequestBody Feedback feedback) {
    feedbackService.saveFeedback(feedback);
    return ResponseEntity.noContent().build();
  }
}
