package com.nttdata.costoconversion.infrastructure.adapter.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.nttdata.costoconversion.application.input.CarInfoVO;
import com.nttdata.costoconversion.application.input.ModelsVO;
import com.nttdata.costoconversion.application.input.conversion.InputConversionVO;
import com.nttdata.costoconversion.application.output.ConvertionOutputVO;
import com.nttdata.costoconversion.domain.model.ConversionEntity;
import com.nttdata.costoconversion.domain.model.VersionsEntity;
import com.nttdata.costoconversion.domain.repository.ConversionRepository;
import com.nttdata.costoconversion.domain.repository.VersionRepository;
import com.nttdata.costoconversion.domain.service.ConversionService;
import com.nttdata.costoconversion.infrastructure.adapter.util.CoreUtils;

@Service
public class ConversionServiceImplementation implements ConversionService {

    public static final Integer TIME_LIFE = 20;
    public static final String ERROR_MODEL_NOT_FOUND = "Modelo de auto no encontrado";
    public static final String ERROR_CRYPTO_NOT_FOUND = "Criptomoneda no encontrada";
    
    private final static String URL_WS_ACCENT = "https://api-force-sales-75bf4126020f.herokuapp.com/api/versiones/1/1036";
    private final static String URL_WS_TUCSON = "https://api-force-sales-75bf4126020f.herokuapp.com/api/versiones/1/1031";
    private final static String URL_WS_SANTE_FE = "https://api-force-sales-75bf4126020f.herokuapp.com/api/versiones/1/1023";
    private final static String URL_WS_GRAND_I10 = "https://api-force-sales-75bf4126020f.herokuapp.com/api/versiones/1/1038";

    private final static String URL_BTC_PRINCIPAL = "https://http-api.livecoinwatch.com/coins/BTC/about?currency=USD";
    private final static String URL_BTC_SECONDARY = "https://api.coinlore.net/api/ticker/?id=90";
    private final static String URL_ETH_PRINCIPAL = "https://http-api.livecoinwatch.com/coins/ETH/about?currency=USD";
    private final static String URL_ETH_SECONDARY = "https://api.coinlore.net/api/ticker/?id=80";

    @Autowired
    private ConversionRepository conversionRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ConvertionOutputVO createConversion(InputConversionVO data) throws Exception {
        String model = data.getData().getModel().toUpperCase();
        String cryptoCurrency = data.getData().getCryptocurrency().toUpperCase();
        String cacheKey = model + "-" + cryptoCurrency;

        // Check if the conversion data is available in the cache
        ConvertionOutputVO cachedConversion = (ConvertionOutputVO) redisTemplate.opsForValue().get(cacheKey);
        if (cachedConversion != null) {
            return cachedConversion;
        }

        List<CarInfoVO> carsModels = getModels(model);
        double cryptoPrice = getCryptoPrice(cryptoCurrency);

        List<ModelsVO> models = carsModels.stream().map(t -> {
            ModelsVO modelsVO = new ModelsVO();
            modelsVO.setVersion(t.getName());
            modelsVO.setPriceCryptocurrency(t.getFinalPrice() / cryptoPrice);
            modelsVO.setCryptocurrency(cryptoCurrency);
            modelsVO.setModel(model);
            modelsVO.setPriceUsd(t.getFinalPrice());
            return modelsVO;
        }).collect(Collectors.toList());

        ConversionEntity conversionEntity = new ConversionEntity();
        conversionEntity.setConversionId(CoreUtils.generateId());
        conversionEntity.setCryptoCurrency(cryptoCurrency);
        conversionEntity.setModel(model);
        conversionEntity.setTimeLife(TIME_LIFE);

        ConversionEntity conversionEntitySaved = conversionRepository.save(conversionEntity);

        List<VersionsEntity> versions = models.stream().map(t -> {
            VersionsEntity version = new VersionsEntity();
            version.setConversion(conversionEntitySaved);
            version.setVersion(t.getVersion());
            version.setPriceCryptocurrency(t.getPriceCryptocurrency());
            version.setPriceUsd(t.getPriceUsd());
            return version;
        }).collect(Collectors.toList());

        versionRepository.saveAll(versions);

        ConvertionOutputVO conversion = new ConvertionOutputVO();
        conversion.setConversionTimelife(conversionEntity.getTimeLife());
        conversion.setConvertionId(conversionEntity.getConversionId());
        conversion.setVersions(models);
        

        // Cache the conversion data for future requests
        redisTemplate.opsForValue().set(cacheKey, conversion, TIME_LIFE, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(conversionEntity.getConversionId(), conversionEntity, TIME_LIFE, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(conversionEntity.getId().toString(), versions, TIME_LIFE, TimeUnit.MINUTES);

        return conversion;
    }

    private List<CarInfoVO> getModels(String model) throws Exception {
        String urlServiceAuto = selectServiceModels(model);
        return WebClient.create(urlServiceAuto)
                        .get()
                        .retrieve()
                        .bodyToFlux(CarInfoVO.class)
                        .collectList()
                        .block();
    }

    private double getCryptoPrice(String crypto) throws Exception {
        try {
            return fetchCryptoPrice(selectCryptoCurrency(crypto, true), true);
        } catch (WebClientResponseException e) {
            return fetchCryptoPrice(selectCryptoCurrency(crypto, false), false);
        }
    }

    private double fetchCryptoPrice(String url, boolean isPrincipal) throws Exception {
        String responseBody = WebClient.create(url)
                                       .get()
                                       .retrieve()
                                       .bodyToMono(String.class)
                                       .block();

        if (responseBody == null) {
            throw new WebClientResponseException("Empty response", 404, "Not Found", null, null, null);
        }
        return isPrincipal ? parsePrincipalCryptoPrice(responseBody) : parseSecondaryCryptoPrice(responseBody);
    }

    private double parsePrincipalCryptoPrice(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getJSONObject("data").getDouble("lastPrice");
    }

    private double parseSecondaryCryptoPrice(String responseBody) {
        JSONArray jsonArray = new JSONArray(responseBody);
        return jsonArray.getJSONObject(0).getDouble("price_usd");
    }

    static String selectServiceModels(String model) throws Exception {
        return switch (model) {
            case "ACCENT" -> URL_WS_ACCENT;
            case "TUCSON" -> URL_WS_TUCSON;
            case "SANTA FE" -> URL_WS_SANTE_FE;
            case "GRAND I10" -> URL_WS_GRAND_I10;
            default -> throw new Exception(ERROR_MODEL_NOT_FOUND);
        };
    }

    static String selectCryptoCurrency(String crypto, boolean isPrincipal) throws Exception {
        return switch (crypto) {
            case "BTC" -> isPrincipal ? URL_BTC_PRINCIPAL : URL_BTC_SECONDARY;
            case "ETH" -> isPrincipal ? URL_ETH_PRINCIPAL : URL_ETH_SECONDARY;
            default -> throw new Exception(ERROR_CRYPTO_NOT_FOUND);
        };
    }
}
