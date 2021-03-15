package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.dto.prpurchase.PrPurchaseRequestDTO;
import com.jsimplec.prms.errors.ErrorDefinition;
import com.jsimplec.prms.errors.GenericError;
import com.jsimplec.prms.model.PrPurchaseHistoryModel;
import com.jsimplec.prms.repository.PrPlanRepository;
import com.jsimplec.prms.repository.PrPurchaseHistoryRepository;
import com.jsimplec.prms.service.PrPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrPurchaseServiceImpl implements PrPurchaseService {

  private final PrPurchaseHistoryRepository historyRepository;
  private final PrPlanRepository planRepository;

  @Override
  public UUID makePurchase(PrPurchaseRequestDTO req, String username) {
    if (!planRepository.existsById(req.getPrPlanId())) {
      throw new GenericError(ErrorDefinition.PR_PLAN_NOT_FOUND);
    }

    PrPurchaseHistoryModel historyModel = PrPurchaseHistoryModel
        .builder()
        .placeId(req.getPlaceId())
        .prPlanId(req.getPrPlanId())
        .transferId(req.getTransferId())
        .username(username)
        .build();

    return historyRepository.save(historyModel).getId();

  }

}
