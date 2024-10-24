package com.nttdata.costoconversion.infrastructure.adapter.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.nttdata.costoconversion.application.input.purchase.InputPurchaseVO;
import com.nttdata.costoconversion.application.output.PurchaseOutputVO;
import com.nttdata.costoconversion.domain.model.ConversionEntity;
import com.nttdata.costoconversion.domain.model.PurchasesEntity;
import com.nttdata.costoconversion.domain.model.VersionsEntity;
import com.nttdata.costoconversion.domain.repository.ConversionRepository;
import com.nttdata.costoconversion.domain.repository.PurchaseRepository;
import com.nttdata.costoconversion.domain.service.PurchaseService;
import com.nttdata.costoconversion.infrastructure.adapter.util.CoreUtils;

@Service
public class PurchaseServiceImplementation implements PurchaseService {

	public static final String ERROR_CONVERSION_NOT_FOUND = "Conversion no encontrada";

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private ConversionRepository conversionRepository;
	
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

	@Override
	public PurchaseOutputVO savePurchase(InputPurchaseVO data) throws Exception {
		ConversionEntity conversion = null;
		VersionsEntity versionEntity = null;
		PurchasesEntity purchaseEntity = null;
		List<ConversionEntity> conversions = null;
		List<VersionsEntity> versions  = null;
		
		ConversionEntity dataConversion = (ConversionEntity) redisTemplate.opsForValue().get(data.getData().getConvertionId());
		if (dataConversion != null) {
		    versions = (List<VersionsEntity>) redisTemplate.opsForValue().get(dataConversion.getId().toString());

		    if (versions != null && !versions.isEmpty()) {
		        Optional<VersionsEntity> opcionalVersion = versions.stream()
		            .filter(version -> version.getVersion().equals(data.getData().getVersion()))
		            .findFirst();

		        if (opcionalVersion.isPresent()) {
		            versionEntity = opcionalVersion.get();
		        }
		    }
		    
		    conversion = dataConversion;

		} else {
		    conversions = conversionRepository.findByConversionIdAndModelAndCryptoCurrency(
		            data.getData().getConvertionId(), data.getData().getModel(), data.getData().getCryptocurrency());

		    if (conversions.isEmpty()) {
		        throw new Exception(ERROR_CONVERSION_NOT_FOUND);
		    }

		    conversion = conversions.get(0);

		    // Si la conversión tiene versiones, filtrar la versión solicitada
		    Optional<VersionsEntity> opcionalVersion = conversion.getVersions().stream()
		        .filter(version -> version.getVersion().equals(data.getData().getVersion()))
		        .findFirst();

		    if (opcionalVersion.isPresent()) {
		        versionEntity = opcionalVersion.get();
		    } else {
		        throw new Exception("Versión no encontrada.");
		    }		    
		}

		purchaseEntity = new PurchasesEntity();
		purchaseEntity.setConversion(conversion);
		purchaseEntity.setVersions(versionEntity);
		purchaseEntity.setFullName(data.getData().getFullName());
		purchaseEntity.setDate(new Date());		
		purchaseEntity.setCryptoCurrency(conversion.getCryptoCurrency());
		purchaseEntity.setModel(conversion.getModel());
		purchaseEntity.setPurchaseId(CoreUtils.generateId());
		purchaseRepository.save(purchaseEntity);
		
		return generateVO(purchaseEntity,versionEntity);
	
	}
	
	/**
	 * Genera el VO de salida
	 * 
	 * @param purchaseEntity
	 * @param versionEntity
	 * @return
	 */
	private PurchaseOutputVO generateVO(PurchasesEntity purchaseEntity, VersionsEntity versionEntity) {
		
		PurchaseOutputVO purchaseOutputVO = new PurchaseOutputVO();		
		purchaseOutputVO.setFullName(purchaseEntity.getFullName());
		purchaseOutputVO.setVersion(versionEntity.getVersion());
		purchaseOutputVO.setModel(purchaseEntity.getModel());
		purchaseOutputVO.setCryptocurrency(purchaseEntity.getCryptoCurrency());
		purchaseOutputVO.setPurchaseId(purchaseEntity.getPurchaseId());
		purchaseOutputVO.setPriceUsd(versionEntity.getPriceUsd());
		purchaseOutputVO.setPriceCryptocurrency(versionEntity.getPriceCryptocurrency());
		purchaseOutputVO.setDate(CoreUtils.fomatDate(purchaseEntity.getDate()));		
		return purchaseOutputVO;
	}

}
