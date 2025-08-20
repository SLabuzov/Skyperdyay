package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import by.skyperdyay.engine.core.middleware.service.CurrencyEdgeService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CurrencyFacade implements CurrencyEdgeService {

    private final CurrencyDomainService currencyDomainService;

    public CurrencyFacade(CurrencyDomainService currencyDomainService) {
        this.currencyDomainService = currencyDomainService;
    }

    @Override
    public List<CurrencyResponse> getAvailableCurrencies() {
        return currencyDomainService.fetchAllCurrencies()
                .stream()
                .map(entity -> new CurrencyResponse(
                        entity.getCode(),
                        entity.getName(),
                        entity.getSymbol()
                ))
                .toList();
    }
}
