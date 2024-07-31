package com.nttdata.costoconversion.application.input.conversion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputConversionVO {
	private  ConvertionVO data;
}
