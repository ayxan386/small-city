package com.jsimplec.places.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.listener.PlaceModelChangeListener;
import com.jsimplec.places.repository.ChangeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityListenersConfig {

  @Autowired
  public void injectObjectMapper(ObjectMapper objectMapper) {
    PlaceModelChangeListener.objectMapper = objectMapper;
  }

  @Autowired
  public void injectLogRepository(ChangeLogRepository logRepository) {
    PlaceModelChangeListener.logRepository = logRepository;
  }
}
