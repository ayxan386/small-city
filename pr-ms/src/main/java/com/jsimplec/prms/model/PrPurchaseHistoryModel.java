package com.jsimplec.prms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "small_city_pr_buy_history")
public class PrPurchaseHistoryModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private UUID id;

  private String username;
  private String transferId;

  private UUID prPlanId;
  private Integer placeId;

  @CreationTimestamp
  private LocalDateTime createTime;
}
