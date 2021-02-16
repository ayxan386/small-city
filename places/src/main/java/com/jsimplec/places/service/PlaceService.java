package com.jsimplec.places.service;

import com.jsimplec.places.dto.places.PlaceRequestDTO;
import com.jsimplec.places.dto.places.PlaceResponseDTO;

import java.util.List;

public interface PlaceService {
  List<PlaceResponseDTO> getAll();

  PlaceResponseDTO addPlace(PlaceRequestDTO request);

  void updatePlace(PlaceRequestDTO request);

}
