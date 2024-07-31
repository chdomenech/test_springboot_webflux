package com.nttdata.costoconversion.application.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CryptoInfoVO {	
	@JsonProperty("price_usd")
	private Float price;
	
	@JsonProperty("symbol")
	private String symbol;
	
}
