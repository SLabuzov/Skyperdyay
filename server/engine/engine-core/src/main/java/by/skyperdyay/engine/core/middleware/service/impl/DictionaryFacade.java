package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.service.CountryDomainService;
import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import by.skyperdyay.engine.core.domain.service.TimezoneDomainService;
import by.skyperdyay.engine.core.middleware.mapper.CountryMapper;
import by.skyperdyay.engine.core.middleware.mapper.CurrencyMapper;
import by.skyperdyay.engine.core.middleware.mapper.TimezoneMapper;
import by.skyperdyay.engine.core.middleware.model.response.CountryResponse;
import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import by.skyperdyay.engine.core.middleware.model.response.TimezoneResponse;
import by.skyperdyay.engine.core.middleware.service.DictionaryEdgeService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DictionaryFacade implements DictionaryEdgeService {

    private final CurrencyDomainService currencyDomainService;
    private final CurrencyMapper currencyMapper;
    private final CountryDomainService countryDomainService;
    private final CountryMapper countryMapper;
    private final TimezoneDomainService timezoneDomainService;
    private final TimezoneMapper timezoneMapper;

    public DictionaryFacade(CurrencyDomainService currencyDomainService,
                            CurrencyMapper currencyMapper,
                            CountryDomainService countryDomainService,
                            CountryMapper countryMapper,
                            TimezoneDomainService timezoneDomainService,
                            TimezoneMapper timezoneMapper) {
        this.currencyDomainService = currencyDomainService;
        this.currencyMapper = currencyMapper;
        this.countryDomainService = countryDomainService;
        this.countryMapper = countryMapper;
        this.timezoneDomainService = timezoneDomainService;
        this.timezoneMapper = timezoneMapper;
    }

    @Override
    public List<CurrencyResponse> getAvailableCurrencies() {
        return currencyDomainService.fetchAllCurrencies()
                .stream()
                .map(currencyMapper::convert)
                .toList();
    }

    @Override
    public List<CountryResponse> getAvailableCountries() {
        return countryDomainService.fetchAllCountries()
                .stream()
                .map(countryMapper::convert)
                .toList();
    }

    @Override
    public List<TimezoneResponse> getAvailableTimezones() {
        return timezoneDomainService.fetchAllTimezones()
                .stream()
                .map(timezoneMapper::convert)
                .toList();
    }
}
