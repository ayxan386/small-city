package com.jsimplec.prms.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDefinition {
  NOT_IMPLEMENTED("Called method is not implemented", 502, "not_implemented_method"),
  PR_PLAN_ALREADY_EXISTS("PR plan with name %s already exists", 400, "pr_plan_exists"),
  INVALID_METHOD_ARGUMENT("Invalid method argument: %s", 400, "invalid_method_argument");
  private final String message;
  private final int status;
  private final String localKey;

  @Override
  public String toString() {
    return String.format("{message='%s', status=%d}", message, status);
  }
}
