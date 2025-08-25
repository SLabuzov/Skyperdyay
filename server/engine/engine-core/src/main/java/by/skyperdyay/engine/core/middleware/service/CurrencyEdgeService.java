package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import java.util.List;

public interface CurrencyEdgeService {
    List<CurrencyResponse> getAvailableCurrencies();
}
