package com.jsimplec.prms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "payment", timeToLive = 60000)
public class PaymentRedisModel {
  @Id
  private UUID id;
  private String username;
  @Builder.Default
  private RedisStatus status = RedisStatus.PENDING;

  public enum RedisStatus {
    PENDING,
    COMPLETED
  }
}
