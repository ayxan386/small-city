package com.jsimplec.places.error;

import com.jsimplec.places.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class CommonHttpError extends RuntimeException {

  private final String message;
  private final ErrorCodes code;
  private final int status;

  public CommonHttpError(String message, ErrorCodes code, int status) {
    this.message = message;
    this.code = code;
    this.status = status;
  }
}
