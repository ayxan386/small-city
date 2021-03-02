package com.jsimplec.auth.controller;

import com.jsimplec.auth.dto.GenericResponse;
import com.jsimplec.auth.dto.login.EmailLoginRequestDTO;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.dto.register.VerificationRequestDTO;
import com.jsimplec.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<GenericResponse<String>> register(@Valid @RequestBody RegisterRequestDTO req) {
    log.info("Trying to register new user {}", req.getUsername());
    String response = authService.register(req);
    log.info("Registering new user {} was successful", req.getUsername());

    return ResponseEntity.status(CREATED.value()).body(GenericResponse.success(response));
  }

  @PostMapping("/login/email")
  public ResponseEntity<GenericResponse<JwtResponseDTO>> login(@Valid @RequestBody EmailLoginRequestDTO req) {
    log.info("Trying to login user {}", req.getEmail());
    JwtResponseDTO response = authService.login(req);
    log.info("Signing in user {} was successful", req.getEmail());

    return ResponseEntity.status(OK.value()).body(GenericResponse.success(response));
  }

  @PostMapping("/verify")
  public ResponseEntity<GenericResponse<String>> verify(@RequestBody VerificationRequestDTO req) {
    log.info("Trying to verify email");
    authService.verifyUser(req);
    log.info("Successfully to verified email");
    return ResponseEntity.ok(GenericResponse.success("verified"));
  }
}
