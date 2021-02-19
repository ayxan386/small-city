package com.jsimplec.places.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.model.EntityChangeLogModel;
import com.jsimplec.places.repository.ChangeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisChangeLogListener {
  private static final TypeReference<EntityChangeLogModel> logReference = new TypeReference<EntityChangeLogModel>() {
  };
  private final ChangeLogRepository logRepository;
  private final ObjectMapper objectMapper;

  public void handle(String logModel) {
    log.info("Message received {}", logModel);
    try {
      EntityChangeLogModel toSave = objectMapper.readValue(logModel, logReference);
      logRepository.save(toSave);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
