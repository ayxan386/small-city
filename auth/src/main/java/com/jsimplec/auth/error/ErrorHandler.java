package com.jsimplec.auth.error;

import com.jsimplec.auth.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponseDTO> handleIllegalState(IllegalStateException err) {
    return ResponseEntity
        .status(501)
        .body(ErrorResponseDTO
            .builder()
            .date(LocalDateTime.now())
            .message(err.getMessage())
            .status(501)
            .build());
  }
}
