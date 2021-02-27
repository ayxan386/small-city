package com.jsimplec.auth.services.impl;

import com.jsimplec.auth.constants.UserStatus;
import com.jsimplec.auth.dto.login.EmailLoginRequestDTO;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.error.GenericError;
import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.repository.UserRepository;
import com.jsimplec.auth.services.AuthService;
import com.jsimplec.auth.services.AuthUtils;
import com.jsimplec.auth.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final AuthUtils authUtils;
  private final EmailService emailService;

  @Override
  public String register(RegisterRequestDTO request) {
    checkIfUsernameOrEmailIsTaken(request);

    UserModel userModel = saveNewUser(request);

    generateAndSetConfirmationId(userModel);
    userRepository.save(userModel);
    emailService.sendCode(userModel);

    return "Confirmation message is sent to your email";
  }

  private void generateAndSetConfirmationId(UserModel userModel) {
    UUID confirmationId = UUID.randomUUID();
    userModel.setConfirmationId(confirmationId);
  }

  @Override
  public JwtResponseDTO login(EmailLoginRequestDTO request) {
    UserModel userModel = getUserIfExists(request);
    return createJwtAndBuildResponseDTO(userModel.getUsername());
  }

  private UserModel getUserIfExists(EmailLoginRequestDTO request) {
    return userRepository
        .findByEmail(request.getEmail())
        .filter(user -> authUtils.checkPasswordMatching(request.getPassword(), user.getPassword()))
        .orElseThrow(() -> new GenericError("User not found / Passwords don't match", 400));
  }

  private JwtResponseDTO createJwtAndBuildResponseDTO(String username) {
    String jwtToken = authUtils.createToken(username);
    return JwtResponseDTO
        .builder()
        .token(jwtToken)
        .build();
  }

  private UserModel saveNewUser(RegisterRequestDTO request) {
    UserModel userModel = UserModel
        .builder()
        .email(request.getEmail())
        .username(request.getPassword())
        .password(authUtils.hash(request.getPassword()))
        .status(UserStatus.PENDING)
        .build();
    return userRepository.save(userModel);
  }

  private void checkIfUsernameOrEmailIsTaken(RegisterRequestDTO request) {
    userRepository
        .findByEmailOrUsername(request.getEmail(), request.getUsername())
        .ifPresent(u -> {
          throw new GenericError("User already exists", 400);
        });
  }
}
