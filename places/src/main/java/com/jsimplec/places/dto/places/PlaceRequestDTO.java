package com.jsimplec.places.dto.places;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequestDTO {
  private Optional<Long> id;
  private String name;
  private String description;
  private String cords;
  private String category;
}
