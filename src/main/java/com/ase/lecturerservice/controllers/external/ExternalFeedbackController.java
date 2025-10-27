package com.ase.lecturerservice.controllers.external;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.services.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/external/feedback")
@RequiredArgsConstructor
@Tag(name = "External Feedback", description = "External API for retrieving student feedback")
public class ExternalFeedbackController {
  private final FeedbackService feedbackService;

  @GetMapping("/student/{studentUuid}")
  @Operation(
      summary = "Get feedback for a student",
      description = "Retrieves all available feedback for a specific student by their UUID"
  )
  @ApiResponse(responseCode = "200", description = "Successfully retrieved feedback list")
  public ResponseEntity<List<Feedback>> getFeedbackForStudent(
      @Parameter(description = "UUID of the student", required = true)
      @PathVariable String studentUuid
  ) {
    return ResponseEntity.ok(feedbackService.getFeedbackForStudent(studentUuid));
  }
}
