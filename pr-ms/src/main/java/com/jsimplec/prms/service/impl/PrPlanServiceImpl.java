package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.repository.PrPlanRepository;
import com.jsimplec.prms.service.PrPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrPlanServiceImpl implements PrPlanService {

  private final PrPlanRepository planRepository;

  @Override
  public PrPlanResponseDTO addPlan(PrPlanRequestDTO req) {
    throw new IllegalStateException("not implemented yet");
  }

  @Override
  public List<PrPlanResponseDTO> getAllActivePlans() {
    throw new IllegalStateException("not implemented yet");
  }
}
