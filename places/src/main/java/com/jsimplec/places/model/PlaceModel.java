package com.jsimplec.places.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jsimplec.places.listener.PlaceModelChangeListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyMap;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@Table(name = "small_city_PLACES")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(PlaceModelChangeListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;
  private String name;
  @Lob
  private String description;
  @Builder.Default
  private BigDecimal rating = BigDecimal.valueOf(5L);
  private String category;
  @Builder.Default
  private BigInteger adPriority = BigInteger.ZERO;
  private String cords;
  @Builder.Default
  private Boolean isActive = TRUE;
  @Transient
  @JsonIgnore
  @Builder.Default
  private Map<String, String> prevFields = emptyMap();
}
