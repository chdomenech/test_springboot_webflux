package com.nttdata.costoconversion.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.costoconversion.domain.model.PurchasesEntity;


public interface PurchaseRepository extends  JpaRepository<PurchasesEntity, Long> {

	List<PurchasesEntity> findByDateAfterAndDateBeforeAndModelAndCryptoCurrency(Date min, Date max,String model, String cryptoCurrency);
	
}
