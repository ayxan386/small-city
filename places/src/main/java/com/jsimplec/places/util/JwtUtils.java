package com.jsimplec.places.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  public static final String ATTR_USERNAME = "username";
  @Value("${jwt.secret-key}")
  private String secretKey;

  public String getUsername(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
