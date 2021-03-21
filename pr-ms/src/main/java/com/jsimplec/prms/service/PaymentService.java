package com.jsimplec.prms.service;

import com.jsimplec.prms.model.PaymentRedisModel;

import java.util.Optional;

public interface PaymentService {
  void preparePayment(String username);

  Optional<PaymentRedisModel> makePayment(String username);
}
