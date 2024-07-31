package com.nttdata.costoconversion.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.costoconversion.domain.model.ConversionEntity;

public interface ConversionRepository extends JpaRepository<ConversionEntity, Long> {

	List<ConversionEntity> findByConversionIdAndModelAndCryptoCurrency(String conversionId,String model, String cryptoCurrency);
	
}
