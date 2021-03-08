package com.jsimplec.prms.dto.prplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrPlanRequestDTO {
  private String name;
  private BigDecimal cost;
  private Integer priorityPlus;
  private Integer duration;
}
