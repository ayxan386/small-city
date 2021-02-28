package com.jsimplec.auth.services;

import com.jsimplec.auth.model.UserModel;

import java.util.UUID;

public interface EmailService {
  void sendCode(UserModel userModel, UUID confirmationId);
}
