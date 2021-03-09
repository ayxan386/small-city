package com.jsimplec.prms.service;

import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;

import java.util.List;

public interface PrPlanService {
  PrPlanResponseDTO addPlan(PrPlanRequestDTO req);

  List<PrPlanResponseDTO> getAllActivePlans();
}
