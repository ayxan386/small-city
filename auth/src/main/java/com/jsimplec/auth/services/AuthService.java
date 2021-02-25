package com.jsimplec.auth.services;

import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.RegisterResponseDTO;

public interface AuthService {

  RegisterResponseDTO register(RegisterRequestDTO request);

}
