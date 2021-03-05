package com.jsimplec.places.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDefinition {
  NOT_IMPLEMENTED("Called method is not implemented", 502, "not_implemented"),
  MISSING_VALUE("Missing required value", 400, "missing_value"),
  PLACE_ALREADY_EXISTS("Place with name %s already exists", 400, "place_exists"),
  PLACE_NOT_FOUND("Place with name %s not found", 404, "place_not_found");
  private final String message;
  private final int status;
  private final String localKey;
}
