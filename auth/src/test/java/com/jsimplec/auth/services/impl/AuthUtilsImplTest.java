package com.jsimplec.auth.services.impl;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class AuthUtilsImplTest {

  @InjectMocks
  AuthUtilsImpl authUtils;

  @Spy
  PasswordEncoder bcrypt = new BCryptPasswordEncoder();

  @BeforeEach
  void setUp() {
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    byte[] encoded = secretKey.getEncoded();
    String stringKey = DatatypeConverter.printBase64Binary(encoded);
    setField(authUtils, "secretKey", stringKey);
  }

  @Test
  void creatingTokenShouldReturnNonBlackString() {
    String givenUsername = "username";
    String jwt = authUtils.createToken(givenUsername);
    assertThat(jwt).isNotBlank();
  }

  @Test
  void verifyToken() {
    String givenUsername = "username";
    String jwt = authUtils.createToken(givenUsername);
    authUtils.verifyToken(jwt);
    assertThat(jwt).isNotBlank();
  }

  @Test
  void getUsername() {
    String givenUsername = "username";
    String jwt = authUtils.createToken(givenUsername);
    String username = authUtils.getUsername(jwt);
    assertThat(username).isEqualTo(givenUsername);
  }

  @Test
  void hash() {
    String hashedPassword = authUtils.hash("hello_world");
    assertThat(hashedPassword).isNotBlank();
  }

  @Test
  void checkPassword() {
    String password = "hello_world";
    String hashedPassword = authUtils.hash(password);
    boolean res = authUtils.checkPasswordMatching(password, hashedPassword);
    assertThat(res).isTrue();
  }

  @Test
  void checkPasswordNonMatchingCase() {
    String password = "hello_world";
    String someOtherPassword = "bye_bye";
    String hashedPassword = authUtils.hash(password);
    boolean res = authUtils.checkPasswordMatching(someOtherPassword, hashedPassword);
    assertThat(res).isFalse();
  }
}