package com.nttdata.costoconversion.application.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarInfoVO {		
	
	@JsonProperty("VER_CODIGO")
	private Integer code;
	
	@JsonProperty("VER_NOMBRE")
	private String name;
	
	@JsonProperty("VEA_PRECIO_PVP")
	private Float pricePVP;
		
	@JsonProperty("VEA_PRECIO_FINAL")
	private Float finalPrice;
	
	

}