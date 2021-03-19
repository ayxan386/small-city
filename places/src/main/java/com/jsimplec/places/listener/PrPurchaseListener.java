package com.jsimplec.places.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.dto.prpurchase.PrPurchaseModel;
import com.jsimplec.places.error.CommonHttpError;
import com.jsimplec.places.error.ErrorDefinition;
import com.jsimplec.places.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrPurchaseListener {

  private static final TypeReference<PrPurchaseModel> PR_PURCHASE_TYPE = new TypeReference<PrPurchaseModel>() {
  };
  private final ObjectMapper objectMapper;
  private final PlaceRepository placeRepository;

  @KafkaListener(topics = "${kafka.topics.pr-purchase}", containerFactory = "kafkaListenerContainerFactory")
  public void handlePrPurchase(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
    PrPurchaseModel prPurchaseModel = objectMapper.readValue(consumerRecord.value(), PR_PURCHASE_TYPE);
    log.info("Trying to make new purchase {}", prPurchaseModel.getHistoryId());

    placeRepository
        .findById(prPurchaseModel.getPlaceId().longValue())
        .map(place -> {
          place.setAdPriority(place.getAdPriority().add(BigInteger.valueOf(prPurchaseModel.getRatingPlus())));
          return place;
        })
        .ifPresentOrElse(placeRepository::save, () -> {
          throw new CommonHttpError(ErrorDefinition.PLACE_NOT_FOUND, prPurchaseModel.getPlaceId().toString());
        });
  }
}
