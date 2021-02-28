package com.jsimplec.auth.repository;

import com.jsimplec.auth.model.VerificationModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends CrudRepository<VerificationModel, UUID> {
  Optional<VerificationModel> findByVerificationIdAndIsActiveTrue(UUID verificationId);
}
