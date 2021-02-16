package com.jsimplec.places.dto.places;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal rating;
  private String category;
  private BigInteger sortOrder;
}
