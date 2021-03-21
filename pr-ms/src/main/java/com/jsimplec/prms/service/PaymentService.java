package com.jsimplec.prms.service;

import com.jsimplec.prms.model.PaymentRedisModel;

public interface PaymentService {
  void preparePayment(String username);

  PaymentRedisModel makePayment(String username);
}
