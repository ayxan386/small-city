package com.jsimplec.places.util;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  public String getUsername(String token) {
    return Jwts
        .parserBuilder()
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
