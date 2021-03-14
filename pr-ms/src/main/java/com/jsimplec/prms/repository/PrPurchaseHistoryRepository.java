package com.jsimplec.prms.repository;

import com.jsimplec.prms.model.PrPurchaseHistoryModel;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PrPurchaseHistoryRepository extends CrudRepository<PrPurchaseHistoryModel, UUID> {
}
