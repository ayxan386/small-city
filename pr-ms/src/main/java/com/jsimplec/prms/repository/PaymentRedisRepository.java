package com.jsimplec.prms.repository;

import com.jsimplec.prms.model.PaymentRedisModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface PaymentRedisRepository extends CrudRepository<PaymentRedisModel, String>, QueryByExampleExecutor<PaymentRedisModel> {
}
