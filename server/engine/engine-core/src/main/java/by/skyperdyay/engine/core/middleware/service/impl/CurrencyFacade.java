package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import by.skyperdyay.engine.core.middleware.mapper.CurrencyMapper;
import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import by.skyperdyay.engine.core.middleware.service.CurrencyEdgeService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CurrencyFacade implements CurrencyEdgeService {

    private final CurrencyDomainService currencyDomainService;
    private final CurrencyMapper currencyMapper;

    public CurrencyFacade(CurrencyDomainService currencyDomainService,
                          CurrencyMapper currencyMapper) {
        this.currencyDomainService = currencyDomainService;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public List<CurrencyResponse> getAvailableCurrencies() {
        return currencyDomainService.fetchAllCurrencies()
                .stream()
                .map(currencyMapper::convert)
                .toList();
    }
}
