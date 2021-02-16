package com.jsimplec.places.repository;

import com.jsimplec.places.model.PlaceModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends CrudRepository<PlaceModel, Long> {
}
