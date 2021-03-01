package com.jsimplec.auth.repository;

import com.jsimplec.auth.model.VerificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationModel, UUID> {
  Optional<VerificationModel> findByUserIdAndIsActiveTrue(UUID userID);

  @Modifying
  @Transactional
  @Query("update VerificationModel vm set vm.isActive=false where vm.userId=:userId and vm.isActive=true")
  void updateAllOtpByUserId(UUID userId);
}
