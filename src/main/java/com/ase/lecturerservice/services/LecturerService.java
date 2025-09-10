package com.ase.lecturerservice.services;

import java.util.List;
import java.util.UUID;
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
  private static final int SECONDS_PER_MINUTE = 60;
  private final ObjectMapper objectMapper;

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

  //TODO: Changed this to webclient, when exam entity is ready
  public Exam getExamData(UUID examUuid) throws Exception {
    return DummyData.EXAMS.stream()
        .filter(exam -> exam.getUuid().equals(examUuid))
        .findFirst()
        .orElseThrow(() -> new Exception("Exam not found"));
  }

  // TODO: Adjust this when saving data is implemented
  public Grade getGradedExam(UUID studentUuid, UUID examUuid){
    return DummyData.GRADE.stream()
        .filter(grade -> grade.getStudentUuid().equals(studentUuid)
            && grade.getExamUuid().equals(examUuid))
        .findFirst()
        .orElse(null);
  }
}
