package com.jsimplec.places.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.constants.ChangeType;
import com.jsimplec.places.model.EntityChangeLogModel;
import com.jsimplec.places.model.LoggedModel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Slf4j
@Component
@NoArgsConstructor
public class LoggedModelChangeListener {

  private final static TypeReference<Map<String, String>> mapReference = new TypeReference<>() {
  };
  public static ObjectMapper objectMapper;
  public static RedisTemplate<String, String> redisTemplate;
  public List<String> ignoredFields = emptyList();

  @PostLoad
  public void setupPreviousFields(LoggedModel loggedModel) {
    setPreviousField(loggedModel);
  }

  @PostPersist
  public void postSave(LoggedModel loggedModel) {
    saveChanges(loggedModel, ChangeType.INSERT);
  }

  @PostUpdate
  public void postUpdate(LoggedModel loggedModel) {
    saveChanges(loggedModel, ChangeType.UPDATE);
  }

  private void saveChanges(LoggedModel loggedModel, ChangeType changeType) {
    Map<String, String> fields = getFields(loggedModel);
    removeIgnoredFields(fields);

    String id = fields.getOrDefault("id", "absent");

    fields = deleteSameFields(fields, loggedModel.getPrevFields());
    String convertedString = convertObjectToJsonString(fields);

    EntityChangeLogModel changeLogModel = EntityChangeLogModel
        .builder()
        .entityId(id)
        .changeType(changeType)
        .entityName(loggedModel.getClass().getSimpleName())
        .changedFields(convertedString)
        .build();

    redisTemplate.convertAndSend("change_log", convertObjectToJsonString(changeLogModel));
    log.debug("Message sent");
  }

  private Map<String, String> deleteSameFields(Map<String, String> fields, Map<String, String> prevFields) {
    HashMap<String, String> updatedFields = new HashMap<>();
    for (String k : fields.keySet()) {
      String v = prevFields.getOrDefault(k, "null");
      String value = fields.get(k);
      if (!value.equals(v)) {
        updatedFields.put(k, String.format("%s ==> %s", v, value));
      }
    }
    return updatedFields;
  }

  private String convertObjectToJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error("Error while converting to json {}", e.getMessage());
    }
    return "{error}";
  }

  private void removeIgnoredFields(Map<String, String> fields) {
    ignoredFields.forEach(fields::remove);
  }

  private Map<String, String> getFields(LoggedModel loggedModel) {
    return objectMapper.convertValue(loggedModel, mapReference);
  }

  private void setPreviousField(LoggedModel loggedModel) {
    Map<String, String> fields = getFields(loggedModel);
    removeIgnoredFields(fields);
    loggedModel.setPrevFields(fields);
  }

}
