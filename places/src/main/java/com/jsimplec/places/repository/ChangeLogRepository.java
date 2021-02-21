package com.jsimplec.places.repository;

import com.jsimplec.places.model.EntityChangeLogModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChangeLogRepository extends JpaRepository<EntityChangeLogModel, UUID> {
  List<EntityChangeLogModel> findAll(Sort sort);
}
