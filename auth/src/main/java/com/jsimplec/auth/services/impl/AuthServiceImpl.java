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
import com.jsimplec.auth.services.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final AuthUtils authUtils;
  private final OtpService otpService;

  @Override
  public String register(RegisterRequestDTO request) {
    checkIfUsernameOrEmailIsTaken(request);

    UserModel userModel = saveNewUser(request);

    otpService.sendOtp(userModel);

    return "Confirmation message is sent to your email";
  }

  @Override
  public JwtResponseDTO login(EmailLoginRequestDTO request) {
    UserModel userModel = getUserIfExists(request.getEmail());
    checkUserStatus(userModel);
    checkPassword(userModel, request);
    return createJwtAndBuildResponseDTO(userModel.getUsername());
  }

  @Override
  public void verifyUser(VerificationRequestDTO verificationRequest) {
    UserModel userModel = getUserIfExists(verificationRequest.getEmail());
    otpService.verify(verificationRequest, userModel);
    activateUser(userModel);
  }

  private void activateUser(UserModel userModel) {
    userModel.setStatus(UserStatus.ACTIVE);
    userRepository.save(userModel);
  }

  private void checkUserStatus(UserModel userModel) {
    if (ObjectUtils.nullSafeEquals(userModel.getStatus(), UserStatus.PENDING)) {
      throw new GenericError("Account verification is pending", 400);
    } else if (!ObjectUtils.nullSafeEquals(userModel.getStatus(), UserStatus.ACTIVE)) {
      throw new GenericError("Account is either blocked or deleted", 403);
    }
  }

  @Override
  public UserModel getUserIfExists(String email) {
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
