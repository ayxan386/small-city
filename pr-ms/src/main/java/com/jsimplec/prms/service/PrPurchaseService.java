package com.jsimplec.prms.service;

import com.jsimplec.prms.dto.prpurchase.PrPurchaseRequestDTO;

public interface PrPurchaseService {

  void makePurchase(PrPurchaseRequestDTO req);
}
