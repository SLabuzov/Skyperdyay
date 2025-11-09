package by.skyperdyay.engine.analytics.middleware.model.response;

import java.math.BigDecimal;

public record BalancePeriodMetrics(
        int periodInterval,
        BigDecimal incomeTotal,
        BigDecimal expenseTotal,
        BigDecimal netIncomeTotal,
        BigDecimal incomeDiff,
        BigDecimal expenseDiff,
        BigDecimal netIncomeDiff,
        BigDecimal incomePercentage,
        BigDecimal expensePercentage,
        BigDecimal netIncomePercentage
) {
}
