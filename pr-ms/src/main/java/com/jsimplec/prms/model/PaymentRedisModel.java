package com.jsimplec.prms.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@Builder
@RedisHash("payment")
public class PaymentRedisModel {
  @Id
  private UUID id;
  private String username;
}
