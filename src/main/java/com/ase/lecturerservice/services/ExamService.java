package com.ase.lecturerservice.services;

import com.ase.lecturerservice.entities.Exam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {

   public List<Exam> getExamsByLecturer(String lecturerUuid)
      throws HttpStatusCodeException {
    if (lecturerUuid == null || lecturerUuid.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Lecturer name is required");
    }

    // TODO: change this to a webclient call, when the API is ready
    log.info("The Exam from {} has been requested", lecturerUuid);
    return DummyData.EXAMS;
  }
}
