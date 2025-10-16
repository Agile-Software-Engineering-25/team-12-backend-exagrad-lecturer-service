package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ase.lecturerservice.components.ApiResponseFactory;
import com.ase.lecturerservice.dtos.FeedbackDocumentRequest;
import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.dtos.response.ApiResponseWrapper;
import com.ase.lecturerservice.entities.FeedbackDocument;
import com.ase.lecturerservice.services.FeedbackDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(BASE_PATH + "/documents/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback Documents", description = "Operations for managing feedback documents")
@Slf4j
@Validated
public class FeedbackDocumentController {

  private final FeedbackDocumentService feedbackDocumentService;
  private final ApiResponseFactory apiResponseFactory;

  @PostMapping(consumes = {"multipart/form-data"})
  @Operation(
      summary = "Upload feedback document",
      description = "Upload an feedback document with metadata")
  public ResponseEntity<ApiResponseWrapper<FeedbackDocument>> uploadFeedbackDocument(
      @Parameter(description = "Document file to upload") @RequestPart("file")
      MultipartFile file,
      @Parameter(description = "Document metadata") @RequestPart("metadata")
      FeedbackDocumentRequest metadata,
      HttpServletRequest request)
      throws IOException {

    log.info(
        ">>> Received file: "
            + file.getOriginalFilename()
            + " ("
            + file.getContentType()
            + ")");
    FeedbackDocument response = feedbackDocumentService.uploadFeedbackDocument(file, metadata);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(apiResponseFactory.created(response, request.getRequestURI()));
  }

  @GetMapping
  @Operation(
      summary = "Get feedback documents",
      description = "Get feedback documents by student ID or feedback ID") // TODO
  public ResponseEntity<ApiResponseWrapper<List<FeedbackDocumentResponse>>> getDocuments(
      @Parameter(description = "Feedback ID to filter documents") @RequestParam @NotBlank
      String feedbackId,
      HttpServletRequest request) {

    List<FeedbackDocumentResponse> documents =
        feedbackDocumentService.getDocumentsByFeedbackId(feedbackId);

    return ResponseEntity.ok(apiResponseFactory.success(documents, request.getRequestURI()));
  }
}
