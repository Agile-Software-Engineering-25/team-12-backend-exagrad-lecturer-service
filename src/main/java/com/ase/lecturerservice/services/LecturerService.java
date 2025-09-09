package com.ase.lecturerservice.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Grade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LecturerService {
  private final ObjectMapper objectMapper;

  private static final int SECONDS_PER_MINUTE = 60;

  public List<Exam> getExamsByLecturer(String lecturer)
      throws HttpStatusCodeException {
    if (lecturer == null || lecturer.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Lecturer name is required");
    }

    // TODO: change this to a webclient call, when the API is ready
    log.info("The Exam from {} has been requested", lecturer);
    return DummyData.EXAMS;
  }

  public ExamDto convertToExamDto(Exam exam) {
    ExamDto examDto = objectMapper.convertValue(exam, ExamDto.class);
    examDto.setTime(examDto.getTime() / SECONDS_PER_MINUTE);

    return (examDto);
  }

  public List<Exam> getExamDataWithoutSubmissions(UUID examUuid) {
    return DummyData.EXAMS.stream()
        .filter(exam -> exam.getUuid().equals(examUuid))
        .collect(Collectors.toList());
  }

  public List<Grade> getGradedExam(UUID studentUuid) {
    return DummyData.GRADE.stream()
        .filter(grades -> grades.getStudentUuid().equals(studentUuid))
        .collect(Collectors.toList());
  }
}
