package com.jsimplec.prms.errors;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class GenericError extends RuntimeException {

  private final ErrorDefinition errorDefinition;
  private final Object[] args;

  public GenericError(ErrorDefinition errorDefinition, Object... args) {
    super(errorDefinition.getMessage());
    this.errorDefinition = errorDefinition;
    this.args = args;
  }

  public GenericError(ErrorDefinition definition) {
    this(definition, new Object[0]);
  }

  @Override
  public String toString() {
    return String.format("%s with args [%s]", errorDefinition, Arrays.toString(args));
  }
}
