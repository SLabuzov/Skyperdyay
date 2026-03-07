package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.ExchangeRate;
import by.skyperdyay.engine.core.domain.repository.ExchangeRateRepository;
import by.skyperdyay.engine.core.domain.service.ExchangeRateService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateDomainServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateDomainServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public void loadRates(List<ExchangeRate> rates) {
        ExchangeRate first = rates.getFirst();
        LocalDate rateDate = first.getId().getRateDate();
        String baseCurrencyCode = first.getId().getBaseCurrencyCode();

        List<ExchangeRate> availableProviderRates = exchangeRateRepository.findForUpdate(baseCurrencyCode, rateDate);

        List<ExchangeRate> outdatedRates = new ArrayList<>();
        for (ExchangeRate rate : rates) {
            if (availableProviderRates.contains(rate)) {
                int updatedRateIndex = availableProviderRates.indexOf(rate);
                ExchangeRate current = availableProviderRates.get(updatedRateIndex);
                if (current.getRate().compareTo(rate.getRate()) != 0) {
                    current.setRate(rate.getRate());
                    outdatedRates.add(current);
                }
            } else {
                outdatedRates.add(rate);
            }
        }

        if (!outdatedRates.isEmpty()) {
            exchangeRateRepository.saveAll(outdatedRates);
        }
    }
}
