package com.jsimplec.places.service.impl;

import com.jsimplec.places.dto.reviews.ReviewRatingRequestDTO;
import com.jsimplec.places.dto.reviews.ReviewRatingResponseDTO;
import com.jsimplec.places.error.specific.NotImplementedError;
import com.jsimplec.places.service.ReviewRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewRatingServiceImpl implements ReviewRatingService {

  @Override
  public String addReview(ReviewRatingRequestDTO requestDTO) {
    throw new NotImplementedError();
  }

  @Override
  public List<ReviewRatingResponseDTO> getAllReviewsByPlaceId(Long placeId) {
    throw new NotImplementedError();
  }
}
