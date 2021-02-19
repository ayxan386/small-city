package com.jsimplec.places.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.constants.ChangeType;
import com.jsimplec.places.model.EntityChangeLogModel;
import com.jsimplec.places.model.PlaceModel;
import com.jsimplec.places.repository.ChangeLogRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
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
  public static ChangeLogRepository logRepository;
  public List<String> ignoredFields = emptyList();

  @PostPersist
  public void postInsert(PlaceModel place) {
    saveChanges(place, ChangeType.INSERT);
  }

  @PostUpdate
  public void postUpdate(PlaceModel place) {
    saveChanges(place, ChangeType.UPDATE);
  }

  private void saveChanges(PlaceModel place, ChangeType changeType) {
    Map<String, String> fields = getFields(place);
    removeIgnoredFields(fields);
    String convertedString = convertToJsonString(fields);
    log.info("Converted string {}", convertedString);

    EntityChangeLogModel changeLogModel = EntityChangeLogModel
        .builder()
        .changeType(changeType)
        .entityName(place.getClass().getSimpleName())
        .changedFields(convertedString)
        .build();

    logRepository.save(changeLogModel);
  }

  private String convertToJsonString(Map<String, String> fields) {
    try {
      return objectMapper.writeValueAsString(fields);
    } catch (JsonProcessingException e) {
      log.error("Error while converting to json {}", e.getMessage());
    }
    return "{}";
  }

  private void removeIgnoredFields(Map<String, String> fields) {
    ignoredFields.forEach(fields::remove);
  }

  private Map<String, String> getFields(PlaceModel place) {
    return objectMapper.convertValue(place, mapReference);
  }

}