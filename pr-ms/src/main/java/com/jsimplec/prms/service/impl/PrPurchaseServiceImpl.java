package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.dto.prpurchase.PrPurchaseRequestDTO;
import com.jsimplec.prms.errors.GenericError;
import com.jsimplec.prms.service.PrPurchaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jsimplec.prms.errors.ErrorDefinition.NOT_IMPLEMENTED;

@Service
public class PrPurchaseServiceImpl implements PrPurchaseService {

  @Override
  public UUID makePurchase(PrPurchaseRequestDTO req) {
    throw new GenericError(NOT_IMPLEMENTED);
  }
}
