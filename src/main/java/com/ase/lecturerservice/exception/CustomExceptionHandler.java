package com.ase.lecturerservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException iae) {
        return ResponseEntity.badRequest().body(iae.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalErrorHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }
}
