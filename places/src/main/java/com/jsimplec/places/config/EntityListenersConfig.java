package com.jsimplec.places.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.listener.LoggedModelChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class EntityListenersConfig {

  @Autowired
  public void injectObjectMapper(ObjectMapper objectMapper) {
    LoggedModelChangeListener.objectMapper = objectMapper;
  }

  @Autowired
  public void injectLogRepository(RedisTemplate<String, String> redisTemplate) {
    LoggedModelChangeListener.redisTemplate = redisTemplate;
  }
}
