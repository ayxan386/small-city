package com.jsimplec.prms.errors;

import com.jsimplec.prms.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(GenericError.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericError(GenericError err) {
    ErrorDefinition errorDefinition = err.getErrorDefinition();
    String formattedMessage = String.format(errorDefinition.getMessage(), err.getArgs());
    return ResponseEntity.status(errorDefinition.getStatus())
        .body(buildErrorResponse(formattedMessage, errorDefinition.getStatus()));
  }

  private ErrorResponseDTO buildErrorResponse(String formattedMessage, int status) {
    return ErrorResponseDTO
        .builder()
        .message(formattedMessage)
        .detailedMessage(formattedMessage)
        .status(status)
        .date(LocalDateTime.now())
        .build();
  }
}
