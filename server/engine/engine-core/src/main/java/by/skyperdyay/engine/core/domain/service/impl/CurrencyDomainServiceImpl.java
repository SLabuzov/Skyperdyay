package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.repository.CurrencyRepository;
import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CurrencyDomainServiceImpl implements CurrencyDomainService {

    private final CurrencyRepository currencyRepository;

    public CurrencyDomainServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> fetchAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency fetchCurrency(String currencyCode) {
        return currencyRepository
                .findById(currencyCode)
                .orElseThrow();
    }
}
