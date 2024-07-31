package com.nttdata.costoconversion.application.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder	
public class ReportOutputVO {
	private String model;
	private String cryptocurrency;
	private String date;
	private Float usdAmount;
	private Double cryptocurrencyAmount;
}
