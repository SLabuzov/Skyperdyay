package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.engine.core.api.service.ExchangeRatesApiService;
import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.ExchangeRate;
import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import by.skyperdyay.engine.core.domain.service.ExchangeRateService;
import by.skyperdyay.engine.core.middleware.mapper.ExchangeRateMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ExchangeRatesFacade implements ExchangeRatesApiService {

    private final CurrencyDomainService currencyDomainService;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRatesFacade(CurrencyDomainService currencyDomainService,
                               ExchangeRateService exchangeRateService,
                               ExchangeRateMapper exchangeRateMapper) {
        this.currencyDomainService = currencyDomainService;
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    @Override
    @Transactional
    public void saveExchangeRates(List<ExchangeRateData> rates) {
        if (rates == null || rates.isEmpty()) {
            return;
        }

        List<String> availableCurrencyCodes = currencyDomainService
                .fetchAllCurrencies()
                .stream()
                .map(Currency::getCode)
                .toList();

        List<ExchangeRate> externalRates = rates
                .stream()
                .filter(rate -> availableCurrencyCodes.contains(rate.targetCurrencyCode()))
                .map(exchangeRateMapper::convert)
                .toList();

        exchangeRateService.loadRates(externalRates);
    }
}
