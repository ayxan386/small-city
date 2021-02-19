package com.jsimplec.places.config;

import com.jsimplec.places.listener.RedisChangeLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    StringRedisSerializer serializer = new StringRedisSerializer();
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setKeySerializer(serializer);
    return redisTemplate;
  }

  @Bean
  public MessageListenerAdapter listenerAdapter(RedisChangeLogListener listener) {
    return new MessageListenerAdapter(listener, "handle");
  }

  @Bean
  public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter) {

    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(listenerAdapter, new PatternTopic("change_log"));
    return container;
  }

}