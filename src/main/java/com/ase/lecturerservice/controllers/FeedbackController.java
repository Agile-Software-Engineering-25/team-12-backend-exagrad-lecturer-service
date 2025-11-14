package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.services.FeedbackService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/feedback")
@RequiredArgsConstructor
public class FeedbackController {
  private final FeedbackService feedbackService;

  @GetMapping("/for-lecturer/{lecturerUuid}")
  public ResponseEntity<List<FeedbackResponse>> getFeedbacksForLecturer(
      @PathVariable String lecturerUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackForLecturer(lecturerUuid));
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<FeedbackResponse> saveFeedback(
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @RequestPart("feedbackData") FeedbackRequest feedbackData) {
    try {
      return ResponseEntity.ok(feedbackService.saveFeedback(feedbackData, files));
    }
    catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping(value = "/{uuid}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<FeedbackResponse> updateFeedback(
      @PathVariable String uuid,
      @RequestPart(value = "files", required = false) MultipartFile[] files,
      @RequestPart(value = "oldFiles", required = false) FeedbackDocumentResponse[] oldFiles,
      @RequestPart Feedback updateFeedback
  ) {
    return ResponseEntity.ok(feedbackService.updateFeedback(uuid, updateFeedback, files, oldFiles));
  }

  @PostMapping("/submit")
  public ResponseEntity<Void> submitFeedback(@RequestBody List<Feedback> feedbacks) {
    feedbackService.submitFeedback(feedbacks);
    return ResponseEntity.noContent().build();
  }
}
