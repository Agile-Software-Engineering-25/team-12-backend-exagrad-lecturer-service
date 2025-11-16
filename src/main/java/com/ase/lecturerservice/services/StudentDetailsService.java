package com.ase.lecturerservice.services;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.dtos.StudentDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentDetailsService {

  private final WebClient webClient;
  private List<StudentDataResponse.StudentDto> cachedStudents = Collections.emptyList();

  @Value("${app.apis.student-data-service.baseurl}")
  private String studentDataServiceBaseUrl;

  @Scheduled(fixedDelay = 10, timeUnit = java.util.concurrent.TimeUnit.SECONDS)
  private void fetchAllStudentDetails() {
    try {
      String url = studentDataServiceBaseUrl + "/users?userType=student";
      this.cachedStudents = fetchListFromApi(url, StudentDataResponse.StudentDto.class);
    } catch (Exception e) {
      log.error("Failed to fetch students details: {}", e.getMessage());
    }
  }

  public List<StudentDataResponse.StudentDto> getAllStudentsDetails() {
    if (cachedStudents.isEmpty()) {
      fetchAllStudentDetails();
    }
    return cachedStudents;
  }

  private <T> List<T> fetchListFromApi(String apiPath, Class<T> responseType) {
    try {
      List<T> responseDtos =
          webClient.get()
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
    } catch (Exception e) {
      log.error("Failed to fetch data from {}: {}", apiPath, e.getMessage(), e);
      throw new ResponseStatusException(
          HttpStatus.SERVICE_UNAVAILABLE,
          "Failed to fetch data from external service", e);
    }
  }

}
