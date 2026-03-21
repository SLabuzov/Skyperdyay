package by.skyperdyay.engine.analytics.domain.model;

import java.math.BigDecimal;

public record TotalBalance(
        String currencyCode,
        String mainCurrencyCode,
        BigDecimal totalBalance,
        BigDecimal totalBalanceMcc
) {
}
