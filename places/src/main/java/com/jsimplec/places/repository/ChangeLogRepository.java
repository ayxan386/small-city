package com.jsimplec.places.repository;

import com.jsimplec.places.model.EntityChangeLogModel;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChangeLogRepository extends CrudRepository<EntityChangeLogModel, UUID> {
}
