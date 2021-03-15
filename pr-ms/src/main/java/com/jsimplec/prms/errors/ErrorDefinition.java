package com.jsimplec.prms.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDefinition {
  NOT_IMPLEMENTED("Called method is not implemented", 502, "not_implemented_method"),
  PR_PLAN_ALREADY_EXISTS("PR plan with name %s already exists", 400, "pr_plan_exists"),
  INVALID_METHOD_ARGUMENT("Invalid method argument: %s", 400, "invalid_method_argument"),
  TOKEN_EXPIRED("Token expired", 401, "token_expired"),
  WRONG_HEADER_STRUCTURE("Invalid header structure", 400, "wrong_header"),
  AUTH_HEADER_NOT_PRESENT("Authorization header is not present", 401, "auth_header_missing");
  private final String message;
  private final int status;
  private final String localKey;

  @Override
  public String toString() {
    return String.format("{message='%s', status=%d}", message, status);
  }
}
