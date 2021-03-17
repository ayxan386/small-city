package com.jsimplec.places.dto.prpurchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrPurchaseModel {
  private Integer duration;
  private Integer ratingPlus;
  private Integer placeId;
  private UUID planId;
  private UUID historyId;
}
