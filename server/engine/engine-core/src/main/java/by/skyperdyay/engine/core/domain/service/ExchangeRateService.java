package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.ExchangeRate;
import java.util.List;

public interface ExchangeRateService {

    void loadRates(List<ExchangeRate> rates);
}
