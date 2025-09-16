package com.ase.lecturerservice.controllers;


import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;
import java.util.HashMap;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import com.ase.lecturerservice.services.SubmissionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_PATH + "/submissions")
@RequiredArgsConstructor
public class SubmissionsController {

  private final SubmissionService submissionService;

  @GetMapping("/exam")
  public List<HashMap> getSubmissions(@RequestParam String examId) throws HttpStatusCodeException {
    System.out.println(examId);

    return submissionService.getSubmissionsByExam(examId);
  }
}
