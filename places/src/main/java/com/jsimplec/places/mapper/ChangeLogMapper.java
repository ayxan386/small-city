package com.jsimplec.places.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.dto.log.LogResponseDTO;
import com.jsimplec.places.model.EntityChangeLogModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ChangeLogMapper {

  @Mapping(source = "changedFields", target = "changedFields", qualifiedByName = "stringToMap")
  LogResponseDTO mapToResponse(EntityChangeLogModel en);

  @Named("stringToMap")
  default Map<String, Object> stringToMap(String fields) {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
    };
    try {
      return objectMapper.readValue(fields, typeReference);
    } catch (JsonProcessingException e) {
      // ignoring exception
    }
    return emptyMap();
  }
}
