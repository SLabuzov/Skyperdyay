package by.skyperdyay.exchange.byn.service;

import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.engine.core.api.service.ExchangeRatesApiService;
import by.skyperdyay.exchange.api.CurrencyExchangeRatesProvider;
import by.skyperdyay.exchange.byn.client.NbrbApiClient;
import by.skyperdyay.exchange.byn.client.NbrbRateResponse;
import by.skyperdyay.exchange.byn.mapper.NbrbRateMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Реализация CurrencyExchangeRatesProvider для белорусского рубля (BYN).
 * Получает курсы обмена от Национального банка Республики Беларусь.
 */
@Slf4j
@Service
public class NbrbExchangeRatesProvider implements CurrencyExchangeRatesProvider {

    private static final String PROVIDER_CODE = "nbrb-byn";
    private static final String BASE_CURRENCY = "BYN";

    private final NbrbApiClient apiClient;
    private final NbrbRateMapper rateMapper;
    private final ExchangeRatesApiService saveService;

    public NbrbExchangeRatesProvider(NbrbApiClient apiClient,
                                     NbrbRateMapper rateMapper,
                                     ExchangeRatesApiService saveService) {
        this.apiClient = apiClient;
        this.rateMapper = rateMapper;
        this.saveService = saveService;
    }

    @Override
    public String getProviderCode() {
        return PROVIDER_CODE;
    }

    @Override
    public String getBaseCurrencyCode() {
        return BASE_CURRENCY;
    }

    @Override
    public void fetchRatesSince(LocalDate fromDate) {
        log.info("Получение обменных курсов BYN из API НБРБ за дату: {}", fromDate);

        // Получение курсов от API НБРБ
        List<NbrbRateResponse> apiResponses = apiClient.fetchRates(fromDate);

        // Преобразование в ExchangeRateData
        List<ExchangeRateData> rates = apiResponses
                .stream()
                .map(item -> rateMapper.convert(item, fromDate))
                .toList();

        log.info("Обработано {} валютные пары из API НБРБ", rates.size());

        // Сохранение курсов через основной сервис
        saveService.saveExchangeRates(rates);

        log.info("Успешно сохранены валютные пары из API НБРБ");
    }
}
