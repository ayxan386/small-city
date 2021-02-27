package com.jsimplec.auth.services.impl;

import com.jsimplec.auth.constants.UserStatus;
import com.jsimplec.auth.dto.login.EmailLoginRequestDTO;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.VerificationRequestDTO;
import com.jsimplec.auth.error.GenericError;
import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.repository.UserRepository;
import com.jsimplec.auth.services.AuthService;
import com.jsimplec.auth.services.AuthUtils;
import com.jsimplec.auth.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    startEmailVerification(userModel);

    return "Confirmation message is sent to your email";
  }

  public void startEmailVerification(UserModel userModel) {
    generateAndSetConfirmationId(userModel);
    userModel.setStatus(UserStatus.PENDING);
    userRepository.save(userModel);
    emailService.sendCode(userModel);
  }

  @Override
  public JwtResponseDTO login(EmailLoginRequestDTO request) {
    UserModel userModel = getUserIfExists(request.getEmail());
    checkUserStatus(userModel);
    checkPassword(userModel, request);
    return createJwtAndBuildResponseDTO(userModel.getUsername());
  }

  @Override
  public void verifyUser(VerificationRequestDTO request) {
    UserModel userModel = getUserIfExists(request.getEmail());
    if (checkIfConfirmationIDsMatch(request.getVerificationId(), userModel.getConfirmationId())) {
      activateUser(userModel);
    } else {
      userModel.setConfirmationId(null);
      userModel.setStatus(UserStatus.FROZEN);
      userRepository.save(userModel);
      throw new GenericError("Wrong verification id", 403);
    }
  }

  private void activateUser(UserModel userModel) {
    userModel.setStatus(UserStatus.ACTIVE);
    userModel.setConfirmationId(null);
    userRepository.save(userModel);
  }

  private void checkUserStatus(UserModel userModel) {
    if (ObjectUtils.nullSafeEquals(userModel.getStatus(), UserStatus.PENDING)) {
      throw new GenericError("Email verification is pending", 400);
    } else if (!ObjectUtils.nullSafeEquals(userModel.getStatus(), UserStatus.ACTIVE)) {
      throw new GenericError("Email is either blocked or deleted", 403);
    }
  }

  private boolean checkIfConfirmationIDsMatch(UUID verificationId, UUID confirmationId) {
    return ObjectUtils.nullSafeEquals(verificationId, confirmationId);
  }

  private void generateAndSetConfirmationId(UserModel userModel) {
    UUID confirmationId = UUID.randomUUID();
    userModel.setConfirmationId(confirmationId);
  }

  private UserModel getUserIfExists(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new GenericError("User not found", 404));

  }

  private void checkPassword(UserModel userModel, EmailLoginRequestDTO req) {
    if (!authUtils.checkPasswordMatching(req.getPassword(), userModel.getPassword())) {
      throw new GenericError("Passwords don't match", 403);
    }
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
