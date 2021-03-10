package com.jsimplec.prms.mapper;

import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.model.PrPlanModel;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PrPlanMapper {
  PrPlanModel requestToModel(PrPlanRequestDTO request);

  PrPlanResponseDTO modelToResponse(PrPlanModel model);
}
