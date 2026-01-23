package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Country;
import java.util.List;

public interface CountryDomainService {
    List<Country> fetchAllCountries();

    Country fetchCountry(String countryCode);
}
