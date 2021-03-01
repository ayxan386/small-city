package com.jsimplec.auth.services;

import com.jsimplec.auth.dto.login.EmailLoginRequestDTO;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.VerificationRequestDTO;
import com.jsimplec.auth.model.UserModel;

public interface AuthService {

  String register(RegisterRequestDTO request);

  JwtResponseDTO login(EmailLoginRequestDTO request);

  void verifyUser(VerificationRequestDTO request);

  UserModel getUserIfExists(String email);
}
