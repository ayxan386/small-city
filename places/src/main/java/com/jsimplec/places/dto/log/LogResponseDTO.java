package com.jsimplec.places.dto.log;

import com.jsimplec.places.constants.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponseDTO {
  private UUID id;
  private ChangeType changeType;
  private String entityName;
  private String entityId;
  private Map<String, Object> changedFields;
  private LocalDateTime date;
}
