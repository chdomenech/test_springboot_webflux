package com.nttdata.costoconversion.domain.service;

import com.nttdata.costoconversion.application.input.conversion.InputConversionVO;
import com.nttdata.costoconversion.application.output.ConvertionOutputVO;

public interface ConversionService {
	
	ConvertionOutputVO createConversion(InputConversionVO data) throws Exception;
	
}
