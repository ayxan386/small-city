package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.model.PaymentRedisModel;
import com.jsimplec.prms.repository.PaymentRedisRepository;
import com.jsimplec.prms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jsimplec.prms.model.PaymentRedisModel.RedisStatus.COMPLETED;

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
  public Optional<PaymentRedisModel> makePayment(String username) {
    PaymentRedisModel paymentRedisModel = new PaymentRedisModel();
    paymentRedisModel.setUsername(username);
    paymentRedisModel.setStatus(COMPLETED);
    Example<PaymentRedisModel> example = Example.of(paymentRedisModel);

    AtomicInteger counter = new AtomicInteger(15);
    while (!paymentRedisRepository.exists(example) && counter.getAndAdd(-1) > 0) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        //.asdasd
      }
    }
    paymentRedisRepository.findAll(example).forEach(pr -> log.info("{}", pr));

    return paymentRedisRepository.findOne(Example.of(paymentRedisModel));
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

      paymentRedisModel.setStatus(COMPLETED);
      paymentRedisRepository.save(paymentRedisModel);

      log.info("Done saving completed payment");
    } catch (InterruptedException e) {
      //don't care
    }
  }
}
