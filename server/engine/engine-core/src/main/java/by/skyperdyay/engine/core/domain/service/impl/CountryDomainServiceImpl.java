package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.common.exception.api.ResourceNotFoundException;
import by.skyperdyay.engine.core.domain.model.Country;
import by.skyperdyay.engine.core.domain.repository.CountryRepository;
import by.skyperdyay.engine.core.domain.service.CountryDomainService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CountryDomainServiceImpl implements CountryDomainService {

    private final CountryRepository countryRepository;

    public CountryDomainServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> fetchAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country fetchCountry(String countryCode) {
        return countryRepository
                .findById(countryCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Страна не найдена",
                        String.format("Запрашиваемая страна %s не найдена в системе", countryCode)
                ));
    }
}
