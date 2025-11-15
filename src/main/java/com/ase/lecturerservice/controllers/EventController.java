package com.ase.lecturerservice.controllers;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ase.lecturerservice.dtos.StudentExamStateDto;
import com.ase.lecturerservice.entities.PublishStatus;
import com.ase.lecturerservice.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH + "/event")
public class EventController {
  private final FeedbackService feedbackService;

  @PostMapping("/feedback/approved")
  private void feedbackApprovedCallback(@RequestBody StudentExamStateDto studentExamStateDto) {
    log.info("Feedbacks has been approved: {}", studentExamStateDto.toString());
    feedbackService.updateFeedbackStatus(studentExamStateDto, PublishStatus.APPROVED);
    feedbackService.sendFeedbackReceivedNotification(studentExamStateDto);
  }

  @PostMapping("/feedback/rejected")
  private void feedbackRejectedCallback(@RequestBody StudentExamStateDto studentExamStateDto) {
    log.info("Feedbacks has been rejected: {}", studentExamStateDto.toString());
    feedbackService.updateFeedbackStatus(studentExamStateDto, PublishStatus.REJECTED);
  }
}
