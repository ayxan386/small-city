package com.jsimplec.places.controller;

import com.jsimplec.places.dto.GenericResponseDTO;
import com.jsimplec.places.dto.places.PlaceResponseDTO;
import com.jsimplec.places.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {
  private final PlaceService placeService;

  @GetMapping
  public GenericResponseDTO<List<PlaceResponseDTO>> getAll() {
    log.info("Getting all places");
    List<PlaceResponseDTO> allPlaces = placeService.getAll();
    log.info("Getting all places was successful");
    return GenericResponseDTO.success(allPlaces);
  }
}
