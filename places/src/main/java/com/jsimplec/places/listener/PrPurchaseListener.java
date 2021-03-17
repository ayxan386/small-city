package com.jsimplec.places.listener;

import com.jsimplec.places.dto.prpurchase.PrPurchaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrPurchaseListener {

  @KafkaListener(topics = "${kafka.topics.pr-purchase}", containerFactory = "kafkaListenerContainerFactory")
  public void handlePrPurchase(PrPurchaseModel prPurchaseModel) {
    log.info("Trying to make new purchase {}", prPurchaseModel.getHistoryId());
  }
}
