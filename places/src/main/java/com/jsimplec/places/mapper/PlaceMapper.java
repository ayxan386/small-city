package com.jsimplec.places.mapper;

import com.jsimplec.places.dto.places.PlaceRequestDTO;
import com.jsimplec.places.dto.places.PlaceResponseDTO;
import com.jsimplec.places.model.PlaceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PlaceMapper {

  @Mapping(source = "id", ignore = true, target = "id")
  PlaceModel requestToModel(PlaceRequestDTO request);

  @Mapping(source = "adPriority", target = "sortOrder")
  PlaceResponseDTO modelToResponse(PlaceModel model);
}
