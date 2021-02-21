package com.jsimplec.places.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_rating")
@EqualsAndHashCode(callSuper = false)
public class ReviewRatingModel extends LoggedModel {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;
  private String content;
  private String author;
  private BigDecimal rating;
  private Long placeId;
  @CreationTimestamp
  private LocalDateTime date;
}
