package com.jsimplec.places.error.specific;

import com.jsimplec.places.constants.ErrorCodes;
import com.jsimplec.places.error.CommonHttpError;

public class PlaceNotFoundError extends CommonHttpError {
  public PlaceNotFoundError() {
    super("Place not found", ErrorCodes.PLACES_BUSINESS_0003, 404);
  }
}
