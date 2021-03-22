package com.jsimplec.prms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "payment", timeToLive = 30)
public class PaymentRedisModel {
  @Id
  private String id;
  @Builder.Default
  private boolean isReady = false;
}
