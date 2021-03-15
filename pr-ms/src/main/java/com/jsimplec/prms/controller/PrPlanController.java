package com.jsimplec.prms.controller;

import com.jsimplec.prms.dto.GenericResponse;
import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.dto.prpurchase.PrPurchaseRequestDTO;
import com.jsimplec.prms.service.PrPlanService;
import com.jsimplec.prms.service.PrPurchaseService;
import com.jsimplec.prms.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PrPlanController {

  private final PrPlanService planService;
  private final PrPurchaseService purchaseService;

  @PostMapping("/add")
  public ResponseEntity<GenericResponse<PrPlanResponseDTO>> addNewPr(@Validated @RequestBody PrPlanRequestDTO req) {
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

  @PostMapping("/purchase")
  public GenericResponse<UUID> makePurchase(@RequestBody PrPurchaseRequestDTO request,
                                            @RequestAttribute(name = JwtUtils.ATTR_USERNAME) String username) {
    log.info("Trying to make purchase for place {}", request.getPlaceId());
    UUID historyId = purchaseService.makePurchase(request, username);
    log.info("Successfully made purchase for place {}; history id {}", request.getPlaceId(), historyId);

    return GenericResponse.success(historyId);
  }
}
