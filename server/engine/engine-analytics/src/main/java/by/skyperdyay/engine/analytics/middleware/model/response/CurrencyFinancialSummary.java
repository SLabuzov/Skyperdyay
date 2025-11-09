package by.skyperdyay.engine.analytics.middleware.model.response;

import java.math.BigDecimal;

public record CurrencyFinancialSummary(
        boolean isMainCurrency,
        String currencyCode,
        BigDecimal availableBalance,
        BalancePeriodMetrics balanceMetrics
) {
}
