package com.jsimplec.auth.services.impl;

import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.RegisterResponseDTO;
import com.jsimplec.auth.error.GenericError;
import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.repository.UserRepository;
import com.jsimplec.auth.services.AuthService;
import com.jsimplec.auth.services.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final AuthUtils authUtils;

  @Override
  public RegisterResponseDTO register(RegisterRequestDTO request) {
    checkIfUsernameOrEmailIsTaken(request);

    saveNewUser(request);

    return getJwtAndBuildResponseDTO(request.getUsername());
  }

  private RegisterResponseDTO getJwtAndBuildResponseDTO(String username) {
    String jwtToken = authUtils.createToken(username);
    return RegisterResponseDTO
        .builder()
        .token(jwtToken)
        .build();
  }

  private void saveNewUser(RegisterRequestDTO request) {
    UserModel userModel = UserModel
        .builder()
        .email(request.getEmail())
        .username(request.getPassword())
        .password(authUtils.hash(request.getPassword()))
        .build();
    userRepository.save(userModel);
  }

  private void checkIfUsernameOrEmailIsTaken(RegisterRequestDTO request) {
    userRepository
        .findByEmailOrUsername(request.getEmail(), request.getUsername())
        .ifPresent(u -> {
          throw new GenericError("User already exists", 400);
        });
  }
}
