package com.jsimplec.prms.controller;

import com.jsimplec.prms.dto.GenericResponse;
import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.service.PrPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PrPlanController {

  private final PrPlanService planService;

  @PostMapping("/add")
  public ResponseEntity<GenericResponse<PrPlanResponseDTO>> addNewPr(@RequestBody PrPlanRequestDTO req) {
    log.info("Trying to add new pr-plan {}", req.getName());
    PrPlanResponseDTO res = planService.addPlan(req);
    log.info("Successfully  added new pr-plan {}, {}", req.getName(), res.getId());
    return ResponseEntity.ok(GenericResponse.success(res));
  }

  @GetMapping("/all-active")
  public ResponseEntity<GenericResponse<List<PrPlanResponseDTO>>> getAllActive() {
    log.info("Trying to get all active pr-plan");
    List<PrPlanResponseDTO> res = planService.getAllActivePlans();
    log.info("Successfully obtained all active pr-plan");
    return ResponseEntity.ok(GenericResponse.success(res));
  }
}
