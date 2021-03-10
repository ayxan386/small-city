package com.jsimplec.prms.errors;

import com.jsimplec.prms.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.jsimplec.prms.errors.ErrorDefinition.INVALID_METHOD_ARGUMENT;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(GenericError.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericError(GenericError err) {
    log.error("Generic handled {}", err.toString());
    ErrorDefinition errorDefinition = err.getErrorDefinition();
    String formattedMessage = String.format(errorDefinition.getMessage(), err.getArgs());
    return ResponseEntity.status(errorDefinition.getStatus())
        .body(buildErrorResponse(formattedMessage, errorDefinition.getStatus()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidMethodArgument(MethodArgumentNotValidException ex) {
    FieldError field = ex.getFieldError();
    String formattedMessage = String.format("%s can't be %s",
        Objects.requireNonNull(field).getField(),
        ObjectUtils.nullSafeToString(field.getRejectedValue()));
    return handleGenericError(new GenericError(INVALID_METHOD_ARGUMENT, formattedMessage));
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
