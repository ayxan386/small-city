package com.jsimplec.auth.model;

import com.jsimplec.auth.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "small_city_user")
public class UserModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID id;
  private String username;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserStatus status;
  private UUID confirmationId;
  @CreationTimestamp
  private LocalDateTime createDate;
  @UpdateTimestamp
  private LocalDateTime updateDate;
}
