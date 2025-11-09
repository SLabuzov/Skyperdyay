package by.skyperdyay.engine.analytics.domain.model;

import java.math.BigDecimal;

public record BalanceTrend(
        String currencyCode,
        BigDecimal currentBalance,
        BigDecimal baselineTotalIncome,
        BigDecimal baselineTotalExpense,
        BigDecimal currentTotalIncome,
        BigDecimal currentTotalExpense,
        BigDecimal countBaselineTransactions,
        BigDecimal countCurrentTransactions,
        BigDecimal countTotalTransactions
) {
}
