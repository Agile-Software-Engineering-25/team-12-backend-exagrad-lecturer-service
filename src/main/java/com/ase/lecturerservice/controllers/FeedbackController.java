package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.services.FeedbackService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/feedback")
@RequiredArgsConstructor
public class FeedbackController {
  private final FeedbackService feedbackService;

  @GetMapping("/for-lecturer/{lecturerUuid}")
  public ResponseEntity<List<Feedback>> getFeedbacksForLecturer(@PathVariable String lecturerUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackForLecturer(lecturerUuid));
  }

  @GetMapping("/for-exam/{examUuid}")
  public ResponseEntity<List<Feedback>> getFeedbackForExam(@PathVariable String examUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackForExam(examUuid));
  }

  @PostMapping
  public ResponseEntity<Feedback> saveFeedback(@RequestBody Feedback feedback) {
    return ResponseEntity.ok(feedbackService.saveFeedback(feedback));
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<Feedback> updateFeedback(
      @PathVariable String uuid,
      @RequestBody Feedback updateFeedback
  ) {
    return ResponseEntity.ok(feedbackService.updateFeedback(uuid, updateFeedback));
  }

  @PostMapping("/submit")
  public ResponseEntity<Void> submitFeedback(@RequestBody List<Feedback> feedbacks) {
    feedbackService.submitFeedback(feedbacks);
    return ResponseEntity.noContent().build();
  }
}
