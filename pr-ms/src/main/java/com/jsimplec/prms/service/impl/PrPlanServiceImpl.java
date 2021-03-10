package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.errors.GenericError;
import com.jsimplec.prms.repository.PrPlanRepository;
import com.jsimplec.prms.service.PrPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jsimplec.prms.errors.ErrorDefinition.NOT_IMPLEMENTED;

@Service
@RequiredArgsConstructor
public class PrPlanServiceImpl implements PrPlanService {

  private final PrPlanRepository planRepository;

  @Override
  public PrPlanResponseDTO addPlan(PrPlanRequestDTO req) {
    throw new GenericError(NOT_IMPLEMENTED);
  }

  @Override
  public List<PrPlanResponseDTO> getAllActivePlans() {
    throw new GenericError(NOT_IMPLEMENTED);
  }
}
