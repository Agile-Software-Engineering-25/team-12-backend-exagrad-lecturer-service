package com.ase.lecturerservice.services;


import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SubmissionService {

  public List<HashMap> getSubmissionsByExam(String examId) throws HttpStatusCodeException {
    if (examId == null || examId.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Exam ID is required."
      );
    }

    HashMap<String, Object> map = new HashMap<>();
    map.put("submissionId", "value");
    map.put("examId", "value2");
    map.put("studentId", "value3");
    map.put("fileUrl", List.of("value4", "value5"));
    map.put("status", "value5");
    map.put("grade", "value6");

    HashMap<String, Object> map2 = new HashMap<>();
    map2.put("submissionId", "valasdsaue");
    map2.put("examId", "valueadas2");
    map2.put("studentId", "vaasdaslue3");
    map2.put("fileUrl", List.of("valasdue4", "valasdue5"));
    map2.put("status", "valuasde5");
    map2.put("grade", "valasdue6");

    HashMap<String, Object> map3 = new HashMap<>();
    map3.put("submissionId", "bla keks");
    map3.put("examId", "valueadas2");
    map3.put("studentId", "vaasdaslue3");
    map3.put("fileUrl", List.of("valasdue4", "valasdue5"));
    map3.put("status", "valuasde5");
    map3.put("grade", "valasdue6");


    return switch (examId) {
      case "1" -> List.of(map);
      case "2" -> List.of(map2, map3);
      default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown exam ID");
    };
  }
}
