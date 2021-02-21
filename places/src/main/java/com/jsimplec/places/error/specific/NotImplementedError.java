package com.jsimplec.places.error.specific;

import com.jsimplec.places.constants.ErrorCodes;
import com.jsimplec.places.error.CommonHttpError;

public class NotImplementedError extends CommonHttpError {
  public NotImplementedError() {
    super("Not implemented", ErrorCodes.PLACES_TECH_0001, 501);
  }
}
