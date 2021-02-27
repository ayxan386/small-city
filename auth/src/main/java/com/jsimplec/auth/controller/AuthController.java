package com.jsimplec.auth.controller;

import com.jsimplec.auth.dto.GenericResponse;
import com.jsimplec.auth.dto.register.JwtResponseDTO;
import com.jsimplec.auth.dto.register.RegisterRequestDTO;
import com.jsimplec.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<GenericResponse<JwtResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO req) {
    log.info("Trying to register new user {}", req.getUsername());
    JwtResponseDTO response = authService.register(req);
    log.info("Registering new user {} was successful", req.getUsername());
    return ResponseEntity.status(CREATED.value()).body(GenericResponse.success(response));
  }
}
