package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.model.PaymentRedisModel;
import com.jsimplec.prms.repository.PaymentRedisRepository;
import com.jsimplec.prms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRedisRepository paymentRedisRepository;

  @Override
  public void preparePayment(String username) {
    try {
      Thread.sleep(1000);
      PaymentRedisModel paymentRedisModel = PaymentRedisModel
          .builder()
          .id(UUID.randomUUID())
          .username(username)
          .build();
      paymentRedisRepository.save(paymentRedisModel);
    } catch (InterruptedException e) {
      //don't care
    }
  }
}
