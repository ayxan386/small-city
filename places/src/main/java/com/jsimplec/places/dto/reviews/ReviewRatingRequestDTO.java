package com.jsimplec.places.dto.reviews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRatingRequestDTO {
  private String content;
  private BigDecimal rating;
  private Long placeId;
}
