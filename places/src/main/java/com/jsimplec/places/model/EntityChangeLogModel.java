package com.jsimplec.places.model;

import com.jsimplec.places.constants.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@Table(name = "change_log")
@NoArgsConstructor
@AllArgsConstructor
public class EntityChangeLogModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID id;
  @Enumerated(EnumType.STRING)
  private ChangeType changeType;
  private String entityName;
  private String entityId;
  @Lob
  private String changedFields;
  @CreationTimestamp
  private LocalDateTime date;
}
