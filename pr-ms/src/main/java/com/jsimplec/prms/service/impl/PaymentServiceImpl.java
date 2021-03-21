package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.model.PaymentRedisModel;
import com.jsimplec.prms.repository.PaymentRedisRepository;
import com.jsimplec.prms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRedisRepository paymentRedisRepository;

  @Override
  public void preparePayment(String username) {
    prepare(username);
  }

  @Override
  public PaymentRedisModel makePayment(String username) {
    Optional<PaymentRedisModel> res = paymentRedisRepository.findByUsername(username);
    while (res.isEmpty()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
//        e.printStackTrace();
      }
      res = paymentRedisRepository.findByUsername(username);
    }

    return res.get();
  }

  @Async
  public void prepare(String username) {
    try {
      PaymentRedisModel paymentRedisModel = PaymentRedisModel
          .builder()
          .id(UUID.randomUUID())
          .username(username)
          .build();
      paymentRedisRepository.save(paymentRedisModel);

      log.info("Done saving pending payment");

      Thread.sleep(10000);

      paymentRedisModel.setStatus(PaymentRedisModel.RedisStatus.COMPLETED);
      paymentRedisRepository.save(paymentRedisModel);

      log.info("Done saving completed payment");
    } catch (InterruptedException e) {
      //don't care
    }
  }
}
