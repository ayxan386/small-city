package com.jsimplec.places.error.specific;

import com.jsimplec.places.constants.ErrorCodes;
import com.jsimplec.places.error.CommonHttpError;

public class PlaceAlreadyExists extends CommonHttpError {

  public PlaceAlreadyExists(String placeName) {
    super(formatMessage(placeName), ErrorCodes.PLACES_BUSINESS_0001, 400);
  }

  private static String formatMessage(String placeName) {
    return String.format("Place with name %s already exists", placeName);
  }
}
