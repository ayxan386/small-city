package com.jsimplec.auth.services.impl;

import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.RegisterResponseDTO;
import com.jsimplec.auth.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  @Override
  public RegisterResponseDTO register(RegisterRequestDTO request) {
    throw new IllegalStateException("Not implemented service");
  }
}
