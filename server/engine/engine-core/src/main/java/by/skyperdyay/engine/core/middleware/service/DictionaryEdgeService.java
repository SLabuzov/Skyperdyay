package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.response.CountryResponse;
import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import by.skyperdyay.engine.core.middleware.model.response.TimezoneResponse;
import java.util.List;

public interface DictionaryEdgeService {
    List<CurrencyResponse> getAvailableCurrencies();

    List<CountryResponse> getAvailableCountries();

    List<TimezoneResponse> getAvailableTimezones();
}
