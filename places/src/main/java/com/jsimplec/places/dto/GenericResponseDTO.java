package com.jsimplec.places.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseDTO<T> {
  private static final String SUCCESS_MESSAGE = "success";
  private T data;
  private String message;

  public static <T> GenericResponseDTO<T> success(T data) {
    return GenericResponseDTO.<T>builder().data(data).message(SUCCESS_MESSAGE).build();
  }
}
