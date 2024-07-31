package com.nttdata.costoconversion.application.input.conversion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConvertionVO {	
	@NotBlank
    @Size(min = 0, max = 20)
	private String model;
	
	@NotBlank
    @Size(min = 0, max = 10)
	private String cryptocurrency;	
}