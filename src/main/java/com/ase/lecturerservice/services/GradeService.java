package com.ase.lecturerservice.services;

import com.ase.lecturerservice.entities.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {

  // TODO: Adjust this when saving data is implemented
  public Grade getGradedExam(String studentUuid, String examUuid){
    return DummyData.GRADE.stream()
        .filter(grade -> grade.getStudentUuid().equals(studentUuid)
            && grade.getExamUuid().equals(examUuid))
        .findFirst()
        .orElse(null);
  }
}
