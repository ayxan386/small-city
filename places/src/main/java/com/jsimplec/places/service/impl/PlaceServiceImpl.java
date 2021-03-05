package com.jsimplec.places.service.impl;

import com.jsimplec.places.dto.places.PlaceRequestDTO;
import com.jsimplec.places.dto.places.PlaceResponseDTO;
import com.jsimplec.places.error.CommonHttpError;
import com.jsimplec.places.mapper.PlaceMapper;
import com.jsimplec.places.model.PlaceModel;
import com.jsimplec.places.repository.PlaceRepository;
import com.jsimplec.places.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import static com.jsimplec.places.error.ErrorDefinition.*;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

  private final PlaceRepository placeRepository;
  private final PlaceMapper placeMapper;

  @Override
  @Transactional
  public List<PlaceResponseDTO> getAll() {
    Spliterator<PlaceModel> allPlacesSplit = placeRepository
        .findAllByIsActiveTrue()
        .spliterator();
    return StreamSupport
        .stream(allPlacesSplit, false)
        .map(placeMapper::modelToResponse)
        .collect(toList());
  }

  @Override
  public PlaceResponseDTO addPlace(PlaceRequestDTO request) {
    if (placeRepository.existsByName(request.getName())) {
      throw new CommonHttpError(PLACE_ALREADY_EXISTS, request.getName());
    }
    PlaceModel toSave = placeMapper.requestToModel(request);
    PlaceModel saved = placeRepository.save(toSave);
    return placeMapper.modelToResponse(saved);
  }

  @Override
  public void updatePlace(PlaceRequestDTO request) {
    placeRepository
        .findById(request.getId().orElseThrow(() -> new CommonHttpError(MISSING_VALUE)))
        .ifPresentOrElse(model -> {
          model.setDescription(request.getDescription());
          model.setName(request.getName());
          model.setCords(request.getCords());
          placeRepository.save(model);
        }, () -> {
          throw new CommonHttpError(PLACE_NOT_FOUND, request.getName());
        });
  }
}
