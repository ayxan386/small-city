package com.jsimplec.prms.errors;

import lombok.Getter;

@Getter
public class GenericError extends RuntimeException {

  private final ErrorDefinition errorDefinition;
  private final Object[] args;

  public GenericError(ErrorDefinition errorDefinition, Object[] args) {
    super(errorDefinition.getMessage());
    this.errorDefinition = errorDefinition;
    this.args = args;
  }

  public GenericError(ErrorDefinition definition) {
    this(definition, null);
  }

}
