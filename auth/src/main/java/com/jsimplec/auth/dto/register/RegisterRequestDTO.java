package com.jsimplec.auth.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
  @NotNull
  @Size(min = 4, max = 32)
  private String username;
  @NotNull
  @Email
  private String email;
  @NotNull
  @Size(min = 8)
  private String password;
}
