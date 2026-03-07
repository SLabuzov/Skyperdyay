package by.skyperdyay.exchange.rub.service;

import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.engine.core.api.service.ExchangeRatesApiService;
import by.skyperdyay.exchange.api.CurrencyExchangeRatesProvider;
import by.skyperdyay.exchange.rub.client.CbrApiClient;
import by.skyperdyay.exchange.rub.client.CbrRateResponse;
import by.skyperdyay.exchange.rub.mapper.CbrRateMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Реализация CurrencyExchangeRatesProvider для российского рубля (RUB).
 * Получает курсы обмена от Центрального банка России.
 */
@Slf4j
@Service
public class CbrExchangeRatesProvider implements CurrencyExchangeRatesProvider {

    private static final String PROVIDER_CODE = "cbr-rub";
    private static final String BASE_CURRENCY = "RUB";

    private final CbrApiClient apiClient;
    private final CbrRateMapper rateMapper;
    private final ExchangeRatesApiService saveService;

    public CbrExchangeRatesProvider(CbrApiClient apiClient,
                                    CbrRateMapper rateMapper,
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
        log.info("Получение обменных курсов RUB из API ЦБ за дату: {}", fromDate);

        // Получаем курсы от API ЦБ
        CbrRateResponse apiResponse = apiClient.fetchRates(fromDate);

        // Маппим в ExchangeRateData
        List<ExchangeRateData> rates = apiResponse.getValutes()
                .stream()
                .map(item -> rateMapper.convert(item, fromDate))
                .toList();

        log.info("Обработано {} валютные пары из API ЦБ", rates.size());

        // Сохраняем курсы через основной сервис
        saveService.saveExchangeRates(rates);

        log.info("Успешно сохранены валютные пары из API ЦБ");
    }
}
