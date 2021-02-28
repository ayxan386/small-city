package com.jsimplec.auth.services.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.jsimplec.auth.constants.UserStatus;
import com.jsimplec.auth.dto.login.EmailLoginRequestDTO;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.VerificationRequestDTO;
import com.jsimplec.auth.error.GenericError;
import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.model.VerificationModel;
import com.jsimplec.auth.repository.UserRepository;
import com.jsimplec.auth.repository.VerificationRepository;
import com.jsimplec.auth.services.AuthService;
import com.jsimplec.auth.services.AuthUtils;
import com.jsimplec.auth.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_NUMBER_GENERATOR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private static final int MAX_ALLOWED_NUMBER_OF_ATTEMPTS = 3;
  private final UserRepository userRepository;
  private final VerificationRepository verificationRepository;
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
    String confirmationId = generateAndReturnConfirmationId(userModel);
    userModel.setStatus(UserStatus.PENDING);
    userRepository.save(userModel);
    emailService.sendCode(userModel, confirmationId);
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
    VerificationModel verificationModel = getActiveVerificationIdIfExists(userModel.getId());
    if (checkVerificationCodes(request.getVerificationId(), verificationModel.getVerificationId())) {
      activateUser(userModel);
      disableVerification(verificationModel);
    } else {
      addAttemptToVerificationAndThrowError(verificationModel);
    }
  }

  private void addAttemptToVerificationAndThrowError(VerificationModel verificationModel) {
    int numberOfAttempts = verificationModel.getNumberOfAttempts() + 1;
    verificationModel.setNumberOfAttempts(numberOfAttempts);

    int remainingRetries = MAX_ALLOWED_NUMBER_OF_ATTEMPTS - numberOfAttempts;

    GenericError err = new GenericError(String.format("Attempt failed. Tries left %d", remainingRetries), 400);

    if (numberOfAttempts > MAX_ALLOWED_NUMBER_OF_ATTEMPTS) {
      verificationModel.setActive(false);
      err = new GenericError("Max number of attempt reached", 409);
    }
    verificationRepository.save(verificationModel);
    throw err;
  }

  private void disableVerification(VerificationModel verificationModel) {
    verificationModel.setActive(false);
    verificationRepository.save(verificationModel);
  }

  private boolean checkVerificationCodes(String userModel, String verificationModel) {
    return ObjectUtils.nullSafeEquals(userModel, verificationModel);
  }

  private VerificationModel getActiveVerificationIdIfExists(UUID userId) {
    return verificationRepository
        .findByUserIdAndIsActiveTrue(userId)
        .orElseThrow(() -> new GenericError("No such user found", 404));
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

  private String generateAndReturnConfirmationId(UserModel userModel) {
    String confirmationId = NanoIdUtils.randomNanoId(
        DEFAULT_NUMBER_GENERATOR,
        "1234567890".toCharArray(),
        7);
    VerificationModel verificationModel = VerificationModel
        .builder()
        .verificationId(confirmationId)
        .isActive(true)
        .userId(userModel.getId())
        .numberOfAttempts(0)
        .build();
    verificationRepository.save(verificationModel);
    return confirmationId;
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
