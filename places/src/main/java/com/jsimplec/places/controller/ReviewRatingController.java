package com.jsimplec.places.controller;

import com.jsimplec.places.dto.GenericResponseDTO;
import com.jsimplec.places.dto.reviews.ReviewRatingRequestDTO;
import com.jsimplec.places.dto.reviews.ReviewRatingResponseDTO;
import com.jsimplec.places.service.ReviewRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRatingController {
  private final ReviewRatingService reviewRatingService;

  @PutMapping
  public GenericResponseDTO<String> addReview(@RequestBody ReviewRatingRequestDTO requestDTO) {
    log.info("Adding review to {}", requestDTO.getPlaceId());
    String res = reviewRatingService.addReview(requestDTO);
    log.info("Added review to {} with status {}", requestDTO.getPlaceId(), res);
    return GenericResponseDTO.success(res);
  }

  @GetMapping("/{placeId}")
  public GenericResponseDTO<List<ReviewRatingResponseDTO>> getReviewsByPlaceID(@PathVariable("placeId") Long placeId) {
    log.info("Getting reviews of {}", placeId);
    List<ReviewRatingResponseDTO> res = reviewRatingService.getAllReviewsByPlaceId(placeId);
    log.info("Getting reviews of {} was successful", placeId);
    return GenericResponseDTO.success(res);
  }
}
