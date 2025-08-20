package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Currency;
import java.util.List;

public interface CurrencyDomainService {

    List<Currency> fetchAllCurrencies();
}
