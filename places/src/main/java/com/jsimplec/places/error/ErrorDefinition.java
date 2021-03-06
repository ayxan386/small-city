package com.jsimplec.places.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDefinition {
  NOT_IMPLEMENTED("Called method is not implemented", 502, "not_implemented"),
  MISSING_VALUE("Missing required value", 400, "missing_value"),
  PLACE_ALREADY_EXISTS("Place with name %s already exists", 400, "place_exists"),
  PLACE_NOT_FOUND("Place with name %s not found", 404, "place_not_found"),
  AUTH_HEADER_NOT_PRESENT("Authorization header is not present", 401, "missing_header"),
  WRONG_HEADER_STRUCTURE("Authorization header doesn't follow the defined format", 401, "bad_auth_header"),
  TOKEN_EXPIRED("Given token is expired", 401, "expired_token");
  private final String message;
  private final int status;
  private final String localKey;
}
