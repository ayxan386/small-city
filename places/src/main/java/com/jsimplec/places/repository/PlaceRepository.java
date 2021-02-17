package com.jsimplec.places.repository;

import com.jsimplec.places.model.PlaceModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<PlaceModel, Long> {

  List<PlaceModel> findAllByIsActiveTrue();

  boolean existsByName(String name);
}
