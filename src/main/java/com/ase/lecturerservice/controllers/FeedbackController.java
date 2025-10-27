package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.services.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  public ResponseEntity<Void> saveFeedback(
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @RequestPart("feedbackData") FeedbackRequest feedbackData) {
    try {

      feedbackService.saveFeedback(feedbackData, files);

      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> saveFeedback(@RequestBody FeedbackRequest dto) {
    try {
      feedbackService.saveFeedback(dto, new MultipartFile[0]);

      return ResponseEntity.status(HttpStatus.CREATED).build();
    } 
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
