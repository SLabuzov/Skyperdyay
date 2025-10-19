package by.skyperdyay.engine.analytics.middleware.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashFlowSummaryResponse(
        BigDecimal incomeAmount,
        BigDecimal expenseAmount,
        LocalDate month,
        String currencyCode
) {
}
