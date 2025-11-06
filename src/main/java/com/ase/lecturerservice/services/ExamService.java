package com.ase.lecturerservice.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.dtos.DataServiceCourseResponse;
import com.ase.lecturerservice.dtos.ExamServiceExamResponse;
import com.ase.lecturerservice.dtos.StudentDataResponse;
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
  @Value("${app.apis.student-data-service.baseurl}")
  private String studentDataServiceBaseUrl;


  public List<Exam> getExamsByLecturer(String lecturerUuid) {
    validateLecturerUuid(lecturerUuid);

    log.info("The Exams from {} has been requested", lecturerUuid);

    List<Exam> allExams = fetchExamsFromExamService();
    List<DataServiceCourseResponse.DataServiceCourseDto> allCourses =
        fetchCoursesFromCourseService();

    Set<String> lecuturerModuleCodes = extractLecturerModuleCodes(allCourses, lecturerUuid);

    List<Exam> lecturerExams =
        filterExamsByModuleCodes(allExams, lecuturerModuleCodes, lecturerUuid);

    return populateExamsWithStudents(lecturerExams);
  }

  private void validateLecturerUuid(String lecturerUuid) {
    if (lecturerUuid == null || lecturerUuid.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Lecturer name is required");
    }
  }

  private Set<String> extractLecturerModuleCodes(
      List<DataServiceCourseResponse.DataServiceCourseDto> courses,
      String lecturerUuid) {
    return courses.stream()
        .filter(course -> isLecturerTeachingCourse(course, lecturerUuid))
        .map(course -> course.getTemplate().getCode())
        .collect(Collectors.toSet());
  }

  private boolean isLecturerTeachingCourse(
      DataServiceCourseResponse.DataServiceCourseDto course,
      String lecturerUuid) {
    return course.getTeachers().stream()
        .anyMatch(teacher -> lecturerUuid.equals(teacher.getExternalId()));
  }

  private List<Exam> filterExamsByModuleCodes(
      List<Exam> exams,
      Set<String> moduleCodes,
      String lecturerUuid) {
    return exams.stream()
        .filter(exam -> moduleCodes.contains(exam.getModule()))
        .peek(exam -> exam.setLecturerUuid(lecturerUuid))
        .toList();
  }

  private <T> List<T> fetchListFromApi(String apiPath, Class<T> responseType) {
    try {
      List<T> responseDtos =
          examServiceWebClient.get()
              .uri(apiPath)
              .retrieve()
              .onStatus(
                  httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                  clientResponse -> clientResponse.bodyToMono(String.class)
                      .map(body -> new ResponseStatusException(
                          clientResponse.statusCode(),
                          "Api call failed: " + body)))
              .bodyToFlux(responseType)
              .collectList()
              .block();
      return responseDtos != null ? responseDtos : Collections.emptyList();
    }
    catch (Exception e) {
      log.error("Failed to fetch data from {}: {}", apiPath, e.getMessage(), e);
      throw new ResponseStatusException(
          HttpStatus.SERVICE_UNAVAILABLE,
          "Failed to fetch data from external service", e);
    }
  }

  private List<Exam> populateExamsWithStudents(List<Exam> exams) {
    if (exams.isEmpty()) {
      return exams;
    }

    List<StudentDataResponse.StudentDto> allStudents = getAllStudentsDetails();
    Map<String, StudentDataResponse.StudentDto> studentMap = createStudentMap(allStudents);

    log.debug("Populate exams with {} students", allStudents.size());

    for (Exam exam : exams) {
      List<Student> studentsForExam = getStudentsForExam(exam.getUuid(), studentMap);
      exam.setAssignedStudents(studentsForExam);
      log.debug("Exam {} assigned to {} students", exam.getUuid(),
          exam.getAssignedStudents().size());
    }

    return exams;
  }

  private Map<String, StudentDataResponse.StudentDto> createStudentMap(
      List<StudentDataResponse.StudentDto> students) {
    return students.stream()
        .collect(Collectors.toMap(
            StudentDataResponse.StudentDto::getId,
            Function.identity(),
            (existing, replacement) -> existing));
  }

  private List<Exam> fetchExamsFromExamService() {
    String url = examServiceBaseUrl + "/api/exams";
    List<ExamServiceExamResponse.ExamServiceExamDto> examDtos =
        fetchListFromApi(url, ExamServiceExamResponse.ExamServiceExamDto.class);

    return examDtos.stream()
        .collect(Collectors.toMap(
            ExamServiceExamResponse.ExamServiceExamDto::getId,
            this::mapToExam,
            (first, second) -> second))
        .values()
        .stream()
        .toList();
  }

  private List<DataServiceCourseResponse.DataServiceCourseDto> fetchCoursesFromCourseService() {
    String url = courseServiceBaseUrl + "/courses/";
    List<DataServiceCourseResponse.DataServiceCourseDto> courseDtos =
        fetchListFromApi(url, DataServiceCourseResponse.DataServiceCourseDto.class);

    return courseDtos.stream()
        .collect(Collectors.toMap(
            DataServiceCourseResponse.DataServiceCourseDto::getId,
            Function.identity(),
            (first, second) -> second))
        .values()
        .stream()
        .toList();

  }

  private Exam mapToExam(ExamServiceExamResponse.ExamServiceExamDto examDto) {
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

  private List<Student> getStudentsForExam(String examUuid,
                                           Map<String, StudentDataResponse.StudentDto> studentMap) {
    try {
      String url = examServiceBaseUrl + "/api/students/exam/" + examUuid;
      List<String> studentsIds = fetchListFromApi(url, String.class);

      return studentsIds.stream()
          .map(studentMap::get)
          .filter(Objects::nonNull)
          .map(studentDto -> objectMapper.convertValue(studentDto, Student.class))
          .toList();
    }
    catch (Exception e) {
      log.error("Failed to fetch data from {}: {}", examUuid, e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  private List<StudentDataResponse.StudentDto> getAllStudentsDetails() {
    try {
      String url = studentDataServiceBaseUrl + "/api/student";
      return fetchListFromApi(url, StudentDataResponse.StudentDto.class);
    }
    catch (Exception e) {
      log.error("Failed to fetch students details: {}", e.getMessage());
      return Collections.emptyList();
    }
  }
}
