package com.jsimplec.prms.dto.prplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrPlanRequestDTO {
  @NotNull
  private String name;
  @NotNull
  @Positive
  private BigDecimal cost;
  @NotNull
  @Positive
  private Integer priorityPlus;
  @NotNull
  @Positive
  private Integer duration;
}
