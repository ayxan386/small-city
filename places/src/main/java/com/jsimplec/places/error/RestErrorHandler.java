package com.jsimplec.places.error;

import com.jsimplec.places.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class RestErrorHandler {

  @ExceptionHandler(CommonHttpError.class)
  ResponseEntity<ErrorResponseDTO> handleCommonError(CommonHttpError error) {
    log.error("{} --> {}", error.getCode(), error.getMessage());
    return ResponseEntity.status(error.getStatus()).body(buildErrorResponse(error));
  }

  private ErrorResponseDTO buildErrorResponse(CommonHttpError error) {
    return ErrorResponseDTO
        .builder()
        .message(error.getMessage())
        .description(error.getMessage())
        .errorCode(error.getCode())
        .time(LocalDateTime.now())
        .status(error.getStatus())
        .build();
  }
}
