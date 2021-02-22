package com.jsimplec.places.service.impl;

import com.jsimplec.places.dto.reviews.ReviewRatingRequestDTO;
import com.jsimplec.places.dto.reviews.ReviewRatingResponseDTO;
import com.jsimplec.places.error.specific.NotImplementedError;
import com.jsimplec.places.error.specific.PlaceNotFoundError;
import com.jsimplec.places.mapper.ReviewRatingMapper;
import com.jsimplec.places.model.ReviewRatingModel;
import com.jsimplec.places.repository.PlaceRepository;
import com.jsimplec.places.repository.ReviewRatingRepository;
import com.jsimplec.places.service.ReviewRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewRatingServiceImpl implements ReviewRatingService {

  private final ReviewRatingRepository ratingRepository;
  private final PlaceRepository placeRepository;
  private final ReviewRatingMapper reviewRatingMapper;

  @Override
  public List<ReviewRatingResponseDTO> getAllReviewsByPlaceId(Long placeId) {
    throw new NotImplementedError();
  }

  @Override
  public String addReview(ReviewRatingRequestDTO requestDTO) {
    checkIfPlaceExists(requestDTO);
    saveReviewToDB(requestDTO);
    BigDecimal averageRating = calculateAverage(requestDTO);
    updateAverageRatingOfPlace(requestDTO, averageRating);
    return "success";
  }

  private void checkIfPlaceExists(ReviewRatingRequestDTO requestDTO) {
    if (!placeRepository.existsById(requestDTO.getPlaceId())) {
      throw new PlaceNotFoundError();
    }
  }

  private void updateAverageRatingOfPlace(ReviewRatingRequestDTO requestDTO, BigDecimal averageRating) {
    placeRepository.findById(requestDTO.getPlaceId())
        .map(place -> {
          place.setRating(averageRating);
          return place;
        })
        .ifPresent(placeRepository::save);
  }

  private void saveReviewToDB(ReviewRatingRequestDTO requestDTO) {
    ReviewRatingModel model = reviewRatingMapper.mapToModel(requestDTO);
    ratingRepository.save(model);
  }

  private BigDecimal calculateAverage(ReviewRatingRequestDTO requestDTO) {
    long totalCount = ratingRepository.countAllByPlaceId(requestDTO.getPlaceId());
    BigDecimal totalSum = ratingRepository.sumAllByPlaceId(requestDTO.getPlaceId());
    return totalSum.divide(BigDecimal.valueOf(totalCount), RoundingMode.FLOOR);
  }

}
