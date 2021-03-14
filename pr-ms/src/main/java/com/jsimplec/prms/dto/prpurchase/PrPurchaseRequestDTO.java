package com.jsimplec.prms.dto.prpurchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrPurchaseRequestDTO {
  private UUID prPlanId;
  private Integer placeId;
  private String transferId;
}
