package com.jsimplec.places.controller;

import com.jsimplec.places.dto.GenericResponseDTO;
import com.jsimplec.places.dto.places.PlaceRequestDTO;
import com.jsimplec.places.dto.places.PlaceResponseDTO;
import com.jsimplec.places.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping
  public GenericResponseDTO<PlaceResponseDTO> addPlace(@RequestBody PlaceRequestDTO req) {
    log.info("Adding new place {}", req.getName());
    PlaceResponseDTO response = placeService.addPlace(req);
    log.info("Place successfully added {}", response.getId());
    return GenericResponseDTO.success(response);
  }

  @PutMapping("/update")
  public void updatePlace(@RequestBody PlaceRequestDTO req) {
    log.info("Updating place {} ....", req.getName());
    placeService.updatePlace(req);
    log.info("Place successfully updated");
  }
}
