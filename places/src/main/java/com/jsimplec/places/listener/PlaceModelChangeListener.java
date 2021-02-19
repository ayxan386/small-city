package com.jsimplec.places.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.constants.ChangeType;
import com.jsimplec.places.model.EntityChangeLogModel;
import com.jsimplec.places.model.PlaceModel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Slf4j
@Component
@NoArgsConstructor
public class PlaceModelChangeListener {

  private final static TypeReference<Map<String, String>> mapReference = new TypeReference<>() {
  };
  public static ObjectMapper objectMapper;
  public static RedisTemplate<String, String> redisTemplate;
  public List<String> ignoredFields = emptyList();
  private Map<String, String> prevFields = Collections.emptyMap();

  @PrePersist
  @PreUpdate
  public void setupPreviousFields(PlaceModel placeModel) {
    setPreviousField(placeModel);
  }

  @PostPersist
  public void postSave(PlaceModel place) {
    saveChanges(place, ChangeType.INSERT);
  }

  @PostUpdate
  public void postUpdate(PlaceModel place) {
    saveChanges(place, ChangeType.UPDATE);
  }

  private void saveChanges(PlaceModel place, ChangeType changeType) {
    Map<String, String> fields = getFields(place);
    removeIgnoredFields(fields);
//    fields = deleteSameFields(fields);
    String convertedString = convertToJsonString(fields);

    log.info("changed fields {}", convertedString);
    EntityChangeLogModel changeLogModel = EntityChangeLogModel
        .builder()
        .changeType(changeType)
        .entityName(place.getClass().getSimpleName())
        .changedFields(convertedString)
        .build();

    redisTemplate.convertAndSend("change_log", convertChangeLogToString(changeLogModel));
    log.info("Message sent");
  }

  private Map<String, String> deleteSameFields(Map<String, String> fields) {
    HashMap<String, String> updatedFields = new HashMap<>();
    for (String k : prevFields.keySet())
      if (fields.containsKey(k)) {
        String v = prevFields.get(k);
        String value = fields.get(k);
        if (!value.equals(v)) {
          updatedFields.put(k, value);
        }
      } else {
        updatedFields.put(k, prevFields.get(k));
      }
    return updatedFields;
  }

  private String convertToJsonString(Map<String, String> fields) {
    try {
      return objectMapper.writeValueAsString(fields);
    } catch (JsonProcessingException e) {
      log.error("Error while converting to json {}", e.getMessage());
    }
    return "{error}";
  }

  private String convertChangeLogToString(EntityChangeLogModel model) {
    try {
      return objectMapper.writeValueAsString(model);
    } catch (JsonProcessingException e) {
      log.error("Error while converting to json {}", e.getMessage());
    }
    return "{error}";
  }

  private void removeIgnoredFields(Map<String, String> fields) {
    ignoredFields.forEach(fields::remove);
  }

  private Map<String, String> getFields(PlaceModel place) {
    return objectMapper.convertValue(place, mapReference);
  }

  private void setPreviousField(PlaceModel placeModel) {
    Map<String, String> fields = getFields(placeModel);
    removeIgnoredFields(fields);
    prevFields = fields;
  }

}
