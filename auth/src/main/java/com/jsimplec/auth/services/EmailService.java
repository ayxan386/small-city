package com.jsimplec.auth.services;

import com.jsimplec.auth.model.UserModel;

public interface EmailService {
  void sendCode(UserModel userModel, String confirmationId);
}
