package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  private final ObjectMapper objectMapper;

  @GetMapping("/for-lecturer/{lecturerUuid}")
  public ResponseEntity<List<FeedbackResponse>> getFeedbacksForLecturer(
      @PathVariable String lecturerUuid) {
    return ResponseEntity.ok(feedbackService.getFeedbackForLecturer(lecturerUuid));
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Void> saveFeedback(
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @RequestParam("feedbackData") String feedbackDataJson) {
    try {
      FeedbackRequest dto = objectMapper.readValue(feedbackDataJson, FeedbackRequest.class);

      feedbackService.saveFeedback(dto, files);

      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
