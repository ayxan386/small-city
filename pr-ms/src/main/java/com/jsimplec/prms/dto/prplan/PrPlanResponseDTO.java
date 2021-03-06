package com.jsimplec.prms.dto.prplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrPlanResponseDTO implements Serializable {
  private UUID id;
  private String name;
  private Integer duration;
  private Integer priorityPlus;
  private BigDecimal cost;
}
