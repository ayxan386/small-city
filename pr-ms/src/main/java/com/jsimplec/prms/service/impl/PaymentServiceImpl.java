package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.model.PaymentRedisModel;
import com.jsimplec.prms.repository.PaymentRedisRepository;
import com.jsimplec.prms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRedisRepository paymentRedisRepository;

  @Async
  @Override
  public void preparePayment(String username) {
    prepare(username);
  }

  @Override
  public Optional<PaymentRedisModel> makePayment(String username) {
    PaymentRedisModel paymentRedisModel = new PaymentRedisModel();
    paymentRedisModel.setId(username);

    AtomicInteger counter = new AtomicInteger(15);
    while (!existsReadyPayment(username) && counter.getAndAdd(-1) > 0) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        log.error("Current thread is interrupted");
        Thread.currentThread().interrupt();
      }
    }

    return paymentRedisRepository.findById(username);
  }

  private boolean existsReadyPayment(String username) {
    return paymentRedisRepository.findById(username)
        .filter(PaymentRedisModel::isReady)
        .isPresent();
  }

  public void prepare(String username) {
    try {
      PaymentRedisModel paymentRedisModel = PaymentRedisModel
          .builder()
          .id(username)
          .build();
      paymentRedisRepository.save(paymentRedisModel);

      log.info("Done saving pending payment");

      Thread.sleep(10000);

      paymentRedisModel.setReady(true);
      paymentRedisRepository.save(paymentRedisModel);

      log.info("Done saving completed payment");
    } catch (InterruptedException e) {
      log.error("Current thread is interrupted");
      Thread.currentThread().interrupt();
    }
  }
}
