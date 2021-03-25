package com.jsimplec.places.filter;

import com.jsimplec.places.error.CommonHttpError;
import com.jsimplec.places.error.ErrorDefinition;
import com.jsimplec.places.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import static com.jsimplec.places.error.ErrorDefinition.AUTH_HEADER_NOT_PRESENT;
import static com.jsimplec.places.error.ErrorDefinition.TOKEN_EXPIRED;

@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

  public static final String AUTH_PREFIX = "Bearer ";

  private final JwtUtils jwtUtils;
  private final HandlerExceptionResolver exceptionResolver;
  private final Map<Pattern, String> allowedPaths = Map.of(Pattern.compile("/places*"), "GET",
      Pattern.compile("/review/.+"), "GET");

  public TokenFilter(JwtUtils jwtUtils,
                     @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
    this.jwtUtils = jwtUtils;
    this.exceptionResolver = exceptionResolver;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    if (!isAuthRequired(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      final String authHeader = verifyAndGetAuthHeaderIfPresent(request);
      final String token = verifyAndGetTokenFromHeader(authHeader);
      verifyExpirationAndDoFilter(request, response, filterChain, token);
    } catch (CommonHttpError err) {
      exceptionResolver.resolveException(request, response, null, err);
    }
  }

  private void verifyExpirationAndDoFilter(HttpServletRequest request,
                                           HttpServletResponse response,
                                           FilterChain filterChain,
                                           String token) throws IOException, ServletException {
    try {
      String username = jwtUtils.getUsername(token);
      request.setAttribute(JwtUtils.ATTR_USERNAME, username);
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException exx) {
      throw new CommonHttpError(TOKEN_EXPIRED);
    }
  }

  private boolean isAuthRequired(HttpServletRequest req) {
    return allowedPaths
        .entrySet()
        .stream()
        .noneMatch(pair -> {
          String requestPath = req.getServletPath();
          String method = req.getMethod();
          boolean pathMatches = pair.getKey().matcher(requestPath).matches();
          boolean methodMatches = method.equalsIgnoreCase(pair.getValue());
          return pathMatches && methodMatches;
        });
  }

  private String verifyAndGetTokenFromHeader(String authHeader) {
    if (authHeader.startsWith(AUTH_PREFIX)) {
      return authHeader.substring(AUTH_PREFIX.length());
    }
    throw new CommonHttpError(ErrorDefinition.WRONG_HEADER_STRUCTURE);
  }

  private String verifyAndGetAuthHeaderIfPresent(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (ObjectUtils.isEmpty(header)) {
      throw new CommonHttpError(AUTH_HEADER_NOT_PRESENT);
    }
    return header;
  }
}
