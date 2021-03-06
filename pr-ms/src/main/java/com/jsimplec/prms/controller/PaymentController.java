package com.jsimplec.prms.controller;

import com.jsimplec.prms.model.PaymentRedisModel;
import com.jsimplec.prms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/prepare")
  public void prepareForPayment(@RequestAttribute("username") String username) {
    log.info("Preparing payment for {}", username);
    paymentService.preparePayment(username);
    log.info("Payment successful");
  }

  @PostMapping("/make")
  public Optional<PaymentRedisModel> makePayment(@RequestAttribute("username") String username) {
    log.info("Making payment for {}", username);
    Optional<PaymentRedisModel> res = paymentService.makePayment(username);
    log.info("Making payment was successful");
    return res;
  }
}
