package com.jsimplec.auth.services.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.jsimplec.auth.dto.register.VerificationRequestDTO;
import com.jsimplec.auth.error.GenericError;
import com.jsimplec.auth.model.UserModel;
import com.jsimplec.auth.model.VerificationModel;
import com.jsimplec.auth.repository.VerificationRepository;
import com.jsimplec.auth.services.EmailService;
import com.jsimplec.auth.services.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_NUMBER_GENERATOR;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

  private static final int MAX_ALLOWED_NUMBER_OF_ATTEMPTS = 3;
  private final VerificationRepository verificationRepository;
  private final EmailService emailService;

  @Override
  public void sendOtp(UserModel user) {
    String confirmationId = generateAndReturnConfirmationId(user);
    emailService.sendCode(user, confirmationId);
  }

  @Override
  public void verify(VerificationRequestDTO request, UserModel userModel) {
    VerificationModel verificationModel = getActiveVerificationIdIfExists(userModel.getId());
    if (checkVerificationCodes(request.getVerificationId(), verificationModel.getVerificationId())) {
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

    if (numberOfAttempts >= MAX_ALLOWED_NUMBER_OF_ATTEMPTS) {
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

  private boolean checkVerificationCodes(String requestCode, String actualCode) {
    return ObjectUtils.nullSafeEquals(requestCode, actualCode);
  }

  private VerificationModel getActiveVerificationIdIfExists(UUID userId) {
    return verificationRepository
        .findByUserIdAndIsActiveTrue(userId)
        .orElseThrow(() -> new GenericError("No such user found", 404));
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

}
