package com.jsimplec.auth.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequestDTO {
  @NotNull
  @Email
  private String email;
  @NotNull
  private UUID verificationId;
}
