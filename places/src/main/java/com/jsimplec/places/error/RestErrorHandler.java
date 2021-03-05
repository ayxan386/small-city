package com.jsimplec.places.error;

import com.jsimplec.places.dto.ErrorMessageDTO;
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
    ErrorDefinition definition = error.getDefinition();
    log.error("{}", error.getMessage());
    return ResponseEntity.status(definition.getStatus()).body(buildErrorResponse(error));
  }

  private ErrorResponseDTO buildErrorResponse(CommonHttpError error) {
    ErrorMessageDTO translatedMessage = translateCommonHttpError(error);
    return ErrorResponseDTO
        .builder()
        .message(translatedMessage.getMessage())
        .description(translatedMessage.getDescription())
        .time(LocalDateTime.now())
        .status(error.getDefinition().getStatus())
        .build();
  }

  private ErrorMessageDTO translateCommonHttpError(CommonHttpError err) {
    String message = String.format(translateString(err.getDefinition().getMessage()), (Object[]) err.getArgs());
    String description = translateString(err.getDefinition().getMessage());

    return ErrorMessageDTO
        .builder()
        .message(message)
        .description(description)
        .build();
  }

  private String translateString(String toTranslate) {
    return toTranslate;
  }
}
