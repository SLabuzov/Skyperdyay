package by.skyperdyay.common.dm;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateData(
        String baseCurrencyCode,
        String targetCurrencyCode,
        BigDecimal rate,
        LocalDate rateDate,
        String source,
        String providerCode) {
}
