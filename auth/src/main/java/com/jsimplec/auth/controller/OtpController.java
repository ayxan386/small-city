package com.jsimplec.auth.controller;

import com.jsimplec.auth.dto.GenericResponse;
import com.jsimplec.auth.dto.otp.SendOtpRequestDTO;
import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.services.AuthService;
import com.jsimplec.auth.services.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
public class OtpController {

  private final OtpService otpService;
  private final AuthService authService;

  @PostMapping("/send")
  public ResponseEntity<GenericResponse<String>> sendOtp(@RequestBody SendOtpRequestDTO req) {
    log.info("Looking for user with email {}", req.getEmail());
    UserModel user = authService.getUserIfExists(req.getEmail());
    log.info("Sending otp to {}...", req.getEmail());
    otpService.sendOtp(user);
    log.info("Sending otp to {} was successful", req.getEmail());
    return ResponseEntity.ok(GenericResponse.success("Email was sent"));
  }
}
