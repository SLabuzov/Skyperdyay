package by.skyperdyay.engine.analytics.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashFlowView(
        LocalDate month,
        String currencyCode,
        BigDecimal incomeAmount,
        BigDecimal expenseAmount
) {
}
