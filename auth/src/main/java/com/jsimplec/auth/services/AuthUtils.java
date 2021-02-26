package com.jsimplec.auth.services;

public interface AuthUtils {
  String createToken(String username);

  String hash(String password);
}
