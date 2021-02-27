package com.jsimplec.auth.services;

import com.jsimplec.auth.dto.login.LoginRequestDTO;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;

public interface AuthService {

  JwtResponseDTO register(RegisterRequestDTO request);

  JwtResponseDTO login(LoginRequestDTO request);

}
