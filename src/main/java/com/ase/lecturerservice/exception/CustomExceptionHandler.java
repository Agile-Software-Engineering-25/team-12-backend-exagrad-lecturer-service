package com.ase.lecturerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(
      IllegalArgumentException illegalArgumentException) {
    return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<String> handleNoResourceFoundException(
      NoResourceFoundException noResourceFoundException
  ) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This page could not be found");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> internalErrorHandler(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.internalServerError().body("Internal Server Error");
  }
}
