package com.ase.lecturerservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(
      IllegalArgumentException illegalArgumentException) {
    return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> internalErrorHandler(Exception exception) {
    log.error(exception.getMessage());
    return ResponseEntity.internalServerError().body("Internal Server Error");
  }
}
