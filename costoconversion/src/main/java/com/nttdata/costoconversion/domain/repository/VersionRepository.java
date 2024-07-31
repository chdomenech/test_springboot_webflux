package com.nttdata.costoconversion.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.costoconversion.domain.model.ConversionEntity;
import com.nttdata.costoconversion.domain.model.VersionsEntity;



public interface VersionRepository extends  JpaRepository<VersionsEntity, Long> {
	
	List<VersionsEntity> findByConversionAndVersion(ConversionEntity conversion, String version);	

}
