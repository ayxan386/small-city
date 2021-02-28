package com.jsimplec.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "small_city_verification")
public class VerificationModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID id;
  private UUID verificationId;
  private UUID userId;
  private boolean isActive;
  @Builder.Default
  private int numberOfAttempts = 0;
  @CreationTimestamp
  private LocalDateTime createdDate;
}
