package com.jsimplec.prms.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDefinition {
  NOT_IMPLEMENTED("Called method is not implemented", 502, "not_implemented_method");
  private final String message;
  private final int status;
  private final String localKey;
}
