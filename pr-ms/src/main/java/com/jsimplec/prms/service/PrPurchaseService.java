package com.jsimplec.prms.service;

import com.jsimplec.prms.dto.prpurchase.PrPurchaseRequestDTO;

import java.util.UUID;

public interface PrPurchaseService {

  UUID makePurchase(PrPurchaseRequestDTO req, String username);
}
