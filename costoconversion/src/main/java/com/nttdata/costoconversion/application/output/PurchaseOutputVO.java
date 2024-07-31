package com.nttdata.costoconversion.application.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder	
public class PurchaseOutputVO {
	private String fullName;
	private String version;
	private String model;
	private String cryptocurrency;
	private Float priceUsd;	
	private Double priceCryptocurrency;
	private String date;
	private String purchaseId;
	
}
