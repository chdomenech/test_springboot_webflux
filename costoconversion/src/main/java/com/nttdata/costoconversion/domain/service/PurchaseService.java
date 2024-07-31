package com.nttdata.costoconversion.domain.service;

import com.nttdata.costoconversion.application.input.purchase.InputPurchaseVO;
import com.nttdata.costoconversion.application.output.PurchaseOutputVO;

public interface PurchaseService {
	
	PurchaseOutputVO savePurchase(InputPurchaseVO data) throws Exception;
}
