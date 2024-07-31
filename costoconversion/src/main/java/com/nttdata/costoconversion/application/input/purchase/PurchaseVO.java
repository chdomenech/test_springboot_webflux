package com.nttdata.costoconversion.application.input.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseVO {
	
	@NotNull
	private String convertionId;		
	@NotNull
	private String fullName;	
	@NotNull
	private String version;	
	@NotNull
	private String model;	
	@NotNull
	private String cryptocurrency;

}
