package com.nttdata.costoconversion.application.input.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputPurchaseVO {
	private  PurchaseVO data;
}
