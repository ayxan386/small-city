package com.jsimplec.prms.mapper;

import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.model.PrPlanModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PrPlanMapper {
  @Mapping(source = "duration", target = "durationInMonths")
  PrPlanModel requestToModel(PrPlanRequestDTO request);

  @Mapping(source = "durationInMonths", target = "duration")
  PrPlanResponseDTO modelToResponse(PrPlanModel model);
}
