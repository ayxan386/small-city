package com.jsimplec.auth.services.impl;

import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Async
  @Override
  public void sendCode(UserModel userModel) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@jsimplec.com");
    message.setTo(userModel.getEmail());
    message.setSubject("Email confirmation");
    message.setText("Verification code: " + userModel.getConfirmationId().toString());
    mailSender.send(message);
  }
}
