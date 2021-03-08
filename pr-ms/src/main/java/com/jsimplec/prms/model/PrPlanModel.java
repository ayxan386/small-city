package com.jsimplec.prms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "small_city_pr_plans")
public class PrPlanModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID id;
  private BigDecimal cost;
  private String name;
  @Column(name = "duration")
  private Integer durationInMonths;
  private Integer priorityPlus;
  @Builder.Default
  private boolean isActive = true;
}
