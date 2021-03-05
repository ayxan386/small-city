package com.jsimplec.places.error;

import lombok.Getter;

@Getter
public class CommonHttpError extends RuntimeException {

  private final ErrorDefinition definition;
  private final Throwable cause;
  private final String[] args;

  public CommonHttpError(ErrorDefinition definition) {
    this(definition, (Throwable) null);
  }

  public CommonHttpError(ErrorDefinition definition, String... args) {
    this(definition, null, args);
  }

  public CommonHttpError(ErrorDefinition definition, Throwable cause, String... args) {
    super(definition.getMessage());
    this.definition = definition;
    this.cause = cause;
    this.args = args;
  }
}
