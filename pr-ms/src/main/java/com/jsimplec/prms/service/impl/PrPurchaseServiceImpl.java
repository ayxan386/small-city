package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.dto.prpurchase.PrPurchaseModel;
import com.jsimplec.prms.dto.prpurchase.PrPurchaseRequestDTO;
import com.jsimplec.prms.errors.ErrorDefinition;
import com.jsimplec.prms.errors.GenericError;
import com.jsimplec.prms.model.PrPlanModel;
import com.jsimplec.prms.model.PrPurchaseHistoryModel;
import com.jsimplec.prms.repository.PrPlanRepository;
import com.jsimplec.prms.repository.PrPurchaseHistoryRepository;
import com.jsimplec.prms.service.PrPurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrPurchaseServiceImpl implements PrPurchaseService {

  private final PrPurchaseHistoryRepository historyRepository;
  private final PrPlanRepository planRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Value("${kafka.topics.pr-purchase}")
  private String prPurchaseTopic;

  @Override
  public UUID makePurchase(PrPurchaseRequestDTO req, String username) {
    PrPlanModel prPlan = planRepository
        .findById(req.getPrPlanId())
        .orElseThrow(() -> new GenericError(ErrorDefinition.PR_PLAN_NOT_FOUND));

    PrPurchaseHistoryModel historyModel = PrPurchaseHistoryModel
        .builder()
        .placeId(req.getPlaceId())
        .prPlanId(req.getPrPlanId())
        .transferId(req.getTransferId())
        .username(username)
        .build();
    historyModel = historyRepository.save(historyModel);

    Map<String, Object> configurationProperties = kafkaTemplate.getProducerFactory().getConfigurationProperties();
    log.info("configs {}", configurationProperties);
    kafkaTemplate.send(prPurchaseTopic, buildPrPurchaseModel(historyModel, prPlan));

    return historyModel.getId();

  }

  private PrPurchaseModel buildPrPurchaseModel(PrPurchaseHistoryModel historyModel, PrPlanModel prPlan) {
    return PrPurchaseModel
        .builder()
        .placeId(historyModel.getPlaceId())
        .planId(prPlan.getId())
        .duration(prPlan.getDurationInMonths())
        .ratingPlus(prPlan.getPriorityPlus())
        .historyId(historyModel.getId())
        .build();
  }

}
