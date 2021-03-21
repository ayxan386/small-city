package com.jsimplec.prms.repository;

import com.jsimplec.prms.model.PaymentRedisModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRedisRepository extends CrudRepository<PaymentRedisModel, UUID> {
  Optional<PaymentRedisModel> findByUsername(String username);
}
