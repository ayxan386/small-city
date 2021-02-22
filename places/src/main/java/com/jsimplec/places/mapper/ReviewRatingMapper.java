package com.jsimplec.places.mapper;

import com.jsimplec.places.dto.reviews.ReviewRatingRequestDTO;
import com.jsimplec.places.dto.reviews.ReviewRatingResponseDTO;
import com.jsimplec.places.model.ReviewRatingModel;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ReviewRatingMapper {
  ReviewRatingModel mapToModel(ReviewRatingRequestDTO requestDTO);

  ReviewRatingResponseDTO mapToResponse(ReviewRatingModel model);
}
