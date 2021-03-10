package com.jsimplec.prms.repository;

import com.jsimplec.prms.model.PrPlanModel;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PrPlanRepository extends CrudRepository<PrPlanModel, UUID> {
  boolean existsByName(String name);
}
