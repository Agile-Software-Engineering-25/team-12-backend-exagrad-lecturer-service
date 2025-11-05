package com.ase.lecturerservice.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.dtos.DataServiceCourseResponse;
import com.ase.lecturerservice.dtos.ExamServiceExamResponse;
import com.ase.lecturerservice.dtos.ExamServiceStudentResponse;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.user.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {
  private final WebClient examServiceWebClient = WebClient.create();
  private final ObjectMapper objectMapper;
  @Value("${app.apis.exam-service.baseurl}")
  private String examServiceBaseUrl;
  @Value("${app.apis.courses-service.baseurl}")
  private String courseServiceBaseUrl;


  public List<Exam> getExamsByLecturer(String lecturerUuid) {
    if (lecturerUuid == null || lecturerUuid.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lecturer name is required");
    }

    log.info("The Exams from {} has been requested", lecturerUuid);
    List<Exam> exams = fetchExamsFromExamService("/api/exams");
    List<DataServiceCourseResponse.DataServiceCourseDto> masterDataCourses =
        fetchCoursesFromCourseService("/courses/");

    Map<String, DataServiceCourseResponse.DataServiceCourseDto> lecturerCourses =
        masterDataCourses.stream()
            .filter(course -> course.getTeachers().stream()
                .anyMatch(teacher -> lecturerUuid.equals(teacher.getExternalId())))
            .collect(Collectors.toMap(
                course -> course.getTemplate().getCode(),
                course -> course,
                (existing, replacement) -> existing
            ));

    List<Exam> lecturerExams = exams.stream()
        .filter(exam -> lecturerCourses.containsKey(exam.getModule()))
        .peek(exam -> {
          exam.setLecturerUuid(lecturerUuid);
        })
        .toList();

    return populateExamsWithStudents("/api/students/exam/{examUuid}", lecturerExams);
  }

  private <T> List<T> fetchListFromApi(String apiPath, Class<T> responseType,
                                       String serviceBaseUrl) {
    List<T> responseDtos =
        examServiceWebClient.get()
            .uri(serviceBaseUrl + apiPath)
            .retrieve()
            .bodyToFlux(responseType)
            .collectList()
            .block();

    return responseDtos != null ? responseDtos : Collections.emptyList();
  }

  private List<Exam> fetchExamsFromExamService(String apiPath) {
    List<ExamServiceExamResponse.ExamServiceExamDto> examDtos =
        fetchListFromApi(apiPath, ExamServiceExamResponse.ExamServiceExamDto.class,
            examServiceBaseUrl);

    return examDtos.stream()
        .collect(Collectors.toMap(
            ExamServiceExamResponse.ExamServiceExamDto::getId,
            Function.identity(),
            (first, second) -> second))
        .values()
        .stream()
        .map(this::parseExam)
        .toList();
  }

  private List<Exam> populateExamsWithStudents(String apiPath, List<Exam> exams) {
    for (Exam exam : exams) {
      try {
        List<ExamServiceStudentResponse.ExamServiceStudentDto> studentDtos =
            examServiceWebClient.get()
                .uri(examServiceBaseUrl + apiPath, exam.getUuid())
                .retrieve()
                .bodyToFlux(ExamServiceStudentResponse.ExamServiceStudentDto.class)
                .collectList()
                .block();

        exam.setAssignedStudents(studentDtos.stream()
            .collect(Collectors.toMap(
                ExamServiceStudentResponse.ExamServiceStudentDto::getId,
                Function.identity(),
                (first, second) -> second))
            .values()
            .stream()
            .map(this::parseStudent)
            .toList());
      }
      catch (Exception e) {
        log.error("Failed to fetch students for exam: {}", exam.getUuid(), e);
        exam.setAssignedStudents(Collections.emptyList());
      }
    }

    return exams;
  }

  private List<DataServiceCourseResponse.DataServiceCourseDto> fetchCoursesFromCourseService(
      String apiPath) {
    List<DataServiceCourseResponse.DataServiceCourseDto> courseDtos =
        fetchListFromApi(apiPath, DataServiceCourseResponse.DataServiceCourseDto.class,
            courseServiceBaseUrl);

    return courseDtos.stream()
        .collect(Collectors.toMap(
            DataServiceCourseResponse.DataServiceCourseDto::getId,
            Function.identity(),
            (first, second) -> second))
        .values()
        .stream()
        .map(this::parseCourse)
        .toList();

  }

  private Exam parseExam(ExamServiceExamResponse.ExamServiceExamDto examDto) {
    if (examDto == null) {
      return null;
    }

    return Exam.builder()
        .uuid(examDto.getId())
        .name(examDto.getTitle())
        .totalPoints(examDto.getMaxPoints())
        .examType(examDto.getExamType())
        .date(examDto.getExamDate())
        .time(examDto.getDuration())
        .attempt(examDto.getAttemptNumber())
        .etcs(examDto.getEtcs())
        .room(examDto.getRoom())
        .module(examDto.getModuleCode())
        .allowedResources(examDto.getTools())
        .fileUploadRequired(examDto.isFileUploadRequired())
        .build();
  }

  private Student parseStudent(
      ExamServiceStudentResponse.ExamServiceStudentDto studentDto) {
    if (studentDto == null) {
      return null;
    }

    return Student.builder()
        .uuid(studentDto.getId())
        .matriculationNumber(studentDto.getMatriculationId())
        .build();
  }

  private DataServiceCourseResponse.DataServiceCourseDto parseCourse(
      DataServiceCourseResponse.DataServiceCourseDto masterDataDto
  ) {
    if (masterDataDto == null) {
      return null;
    }

    return DataServiceCourseResponse.DataServiceCourseDto.builder()
        .id(masterDataDto.getId())
        .teachers(masterDataDto.getTeachers())
        .template(masterDataDto.getTemplate())
        .build();
  }
}
