package by.skyperdyay.engine.analytics.domain.model;

import java.math.BigDecimal;

/**
 * Агрегированные метрики доходов/расходов за два последовательных периода
 * одинаковой длительности: базовый и текущий. Все валютные суммы пересчитываются дополнительно в основную валюту пользователя (*Mcc).
 */
public record PeriodMetrics(
        String currencyCode,
        String mainCurrencyCode,
        BigDecimal baselineTotalIncome,
        BigDecimal baselineTotalIncomeMcc,
        BigDecimal baselineTotalExpense,
        BigDecimal baselineTotalExpenseMcc,
        BigDecimal currentTotalIncome,
        BigDecimal currentTotalIncomeMcc,
        BigDecimal currentTotalExpense,
        BigDecimal currentTotalExpenseMcc,
        BigDecimal countBaselineTransactions,
        BigDecimal countCurrentTransactions,
        BigDecimal countTotalTransactions
) {
}
