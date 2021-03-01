package com.jsimplec.auth.services;

import com.jsimplec.auth.dto.register.VerificationRequestDTO;
import com.jsimplec.auth.model.UserModel;

public interface OtpService {
  void sendOtp(UserModel user);

  void verify(VerificationRequestDTO requestDTO, UserModel userModel);
}
