package com.jsimplec.places.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.Boolean.TRUE;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@Table(name = "small_city_PLACES")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceModel extends LoggedModel {
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

}
