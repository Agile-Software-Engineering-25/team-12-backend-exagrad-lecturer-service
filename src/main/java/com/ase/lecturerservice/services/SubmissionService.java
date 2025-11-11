package com.ase.lecturerservice.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.lecturerservice.dtos.StudentServiceSubmissionResponse;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.entities.Submission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {
  private final ExamService examService;
  private final WebClient webClient;
  @Value("${app.apis.student-service.baseurl}")
  private String studentServiceBaseUrl;

  private List<Submission> executeApiCall(String apiPath) {
    return parseSubmissions(webClient.get()
        .uri(studentServiceBaseUrl + apiPath)
        .exchangeToMono(clientResponse ->
            clientResponse.bodyToMono(StudentServiceSubmissionResponse.class)).block().getData());
  }

  private List<Submission> parseSubmissions(
      List<StudentServiceSubmissionResponse.StudentServiceSubmissionDto> submissionDtos
  ) {
    Map<String, List<StudentServiceSubmissionResponse.StudentServiceSubmissionDto>> fileReferences
        = new HashMap<>();

    submissionDtos.stream().forEach(submissionDto -> {
      String key = submissionDto.getStudentId() + ":" + submissionDto.getExamId();
      List<StudentServiceSubmissionResponse.StudentServiceSubmissionDto> references = fileReferences
          .getOrDefault(key, new LinkedList<>());
      references.add(submissionDto);
      fileReferences.put(key, references);
    });

    return fileReferences.values().stream()
        .map(references -> Submission.builder()
            .uuid(references.getLast().getId())
            .submissionDate(references.getLast().getUploadDate())
            .examUuid(references.getLast().getId())
            .studentUuid(references.getLast().getStudentId())
            .fileUpload(references.stream().map(fileRef ->
                    FileReference.builder()
                        .fileName(fileRef.getFileName())
                        .fileUuid(fileRef.getId()).build())
                .collect(Collectors.toList())).build())
        .collect(Collectors.toList());
  }

  public List<Submission> getSubmissionsForExam(String examId) {
    return executeApiCall("/documents/exams?examId=" + examId);
  }

  public List<Submission> getSubmissionsForStudent(String studentId) {
    return executeApiCall("/documents/exams?studentId=" + studentId);
  }

  public List<Submission> getAllAccessibleSubmissionsForLecturer(String lecturerUuid) {
    Set<String> examsOfLecturer = examService.getExamsByLecturer(lecturerUuid).stream()
        .map(Exam::getUuid).collect(Collectors.toSet());

    return examsOfLecturer.stream()
        .map(this::getSubmissionsForExam)
        .flatMap(java.util.Collection::stream).collect(Collectors.toList());
  }

}
