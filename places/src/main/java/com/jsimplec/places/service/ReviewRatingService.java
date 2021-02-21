package com.jsimplec.places.service;


import com.jsimplec.places.dto.reviews.ReviewRatingRequestDTO;
import com.jsimplec.places.dto.reviews.ReviewRatingResponseDTO;

import java.util.List;

public interface ReviewRatingService {
  String addReview(ReviewRatingRequestDTO requestDTO);

  List<ReviewRatingResponseDTO> getAllReviewsByPlaceId(Long placeId);
}
