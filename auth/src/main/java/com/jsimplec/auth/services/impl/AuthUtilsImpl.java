package com.jsimplec.auth.services.impl;

import com.jsimplec.auth.error.GenericError;
import com.jsimplec.auth.services.AuthUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthUtilsImpl implements AuthUtils {

  public static final String HASHING_ALGORITHM = "SHA-256";
  public static final String KEY_GENERATING_ALGO = "AES";
  @Value("${secrets.jwt.key}")
  private String secretKey;

  @Override
  public String createToken(String username) {
    Key key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), KEY_GENERATING_ALGO);
    return Jwts
        .builder()
        .setSubject(username)
        .signWith(key)
        .compact();
  }

  @Override
  public String hash(String password) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
      byte[] digested = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
      return new String(digested);
    } catch (NoSuchAlgorithmException e) {
      throw new GenericError(e.getMessage(), 500);
    }
  }
}
