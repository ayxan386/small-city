package com.jsimplec.places.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsimplec.places.constants.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
  private String message;
  private ErrorCodes errorCode;
  private String description;
  private int status;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime time;
}
