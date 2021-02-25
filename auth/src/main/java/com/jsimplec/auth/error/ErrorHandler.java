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
    return handle(err.getMessage(), 501);
  }

  @ExceptionHandler(GenericError.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericError(GenericError err) {
    return handle(err.getMessage(), err.getStatus());
  }

  private ResponseEntity<ErrorResponseDTO> handle(String message, int status) {
    return ResponseEntity
        .status(status)
        .body(ErrorResponseDTO
            .builder()
            .date(LocalDateTime.now())
            .message(message)
            .status(status)
            .build());
  }
}