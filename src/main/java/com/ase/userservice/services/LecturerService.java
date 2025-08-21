package com.ase.userservice.services;

import com.ase.userservice.dtos.ExamDto;
import com.ase.userservice.entities.Exam;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LecturerService {
  private final ObjectMapper objectMapper;

  public List<Exam> fetchExamsByLecturer(String lecturer) {
    // TODO: change this to a webclient call, when the API is ready
    log.info("The Exam from {} has been requested", lecturer);
    return DummyData.EXAMS;
  }

  public List<ExamDto> convertToExamDto (List<Exam> exams) {
    if (exams == null) {
      return List.of();
    }

    Map<String, ExamDto> examDtoMap = new HashMap<>();
    for (Exam exam : exams) {
      ExamDto examDto = objectMapper.convertValue(exam, ExamDto.class);
      String key = exam.getName();

      examDto.setTime(examDto.getTime() / 60);

      if (examDtoMap.containsKey(key)) {
        ExamDto existingDto = examDtoMap.get(key);
        existingDto.setCount(existingDto.getCount() + 1);
      } else {
        examDto.setCount(1);
        examDtoMap.put(key, examDto);
      }
    }

    return new ArrayList<>(examDtoMap.values());
  }
}
