package com.jsimplec.places.repository;

import com.jsimplec.places.model.ReviewRatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ReviewRatingRepository extends JpaRepository<ReviewRatingModel, Long> {
  @Query("SELECT sum(r.rating) FROM ReviewRatingModel r where r.placeId=:placeId")
  BigDecimal sumAllByPlaceId(Long placeId);

  long countAllByPlaceId(Long placeId);
}
