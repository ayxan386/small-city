package com.jsimplec.places.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${kafka.host}")
  private String kafkaHost;
  @Value("${kafka.port}")
  private String kafkaPort;

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, Object>
  kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setRetryTemplate(getSimpleRetry());
    return factory;
  }

  private RetryTemplate getSimpleRetry() {
    RetryTemplate retryTemplate = new RetryTemplate();

    SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
    simpleRetryPolicy.setMaxAttempts(10);

    retryTemplate.setRetryPolicy(simpleRetryPolicy);

    FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
    fixedBackOffPolicy.setBackOffPeriod(10000);

    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

    return retryTemplate;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerProps());
  }

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s", kafkaHost, kafkaPort));
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "places-group");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    return props;
  }
}
