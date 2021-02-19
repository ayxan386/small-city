package com.jsimplec.places.error.specific;

import com.jsimplec.places.constants.ErrorCodes;
import com.jsimplec.places.error.CommonHttpError;

public class MissingValueError extends CommonHttpError {
  public MissingValueError() {
    super("Missing required value", ErrorCodes.PLACES_BUSINESS_0002, 400);
  }
}
