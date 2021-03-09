package com.jsimplec.prms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
  private T data;
  private String message;

  public static <T> GenericResponse<T> success(T data) {
    return new GenericResponse<>(data, "success");
  }
}
