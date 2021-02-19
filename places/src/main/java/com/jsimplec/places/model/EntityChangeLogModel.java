package com.jsimplec.places.model;

import com.jsimplec.places.constants.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
  @Lob
  private String changedFields;
  @CreatedDate
  private LocalDateTime date;
}
