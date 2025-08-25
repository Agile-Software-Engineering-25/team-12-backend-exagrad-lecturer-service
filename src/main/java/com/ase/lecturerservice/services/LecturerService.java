package com.ase.lecturerservice.services;

import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LecturerService {
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

  public List<ExamDto> convertToExamDto(List<Exam> exams) {
    if (exams == null) {
      return List.of();
    }

    Map<String, ExamDto> examDtoMap = exams.stream()
        .map(exam -> {
          ExamDto dto = objectMapper.convertValue(exam, ExamDto.class);
          dto.setTime(dto.getTime() / 60);
          dto.setSubmissions(1);
          return Map.entry(exam.getName(), dto);
        })
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (existing, incoming) -> {
              existing.setSubmissions(existing.getSubmissions() + 1);
              return existing;
            }));

    return new ArrayList<>(examDtoMap.values());
  }
}
