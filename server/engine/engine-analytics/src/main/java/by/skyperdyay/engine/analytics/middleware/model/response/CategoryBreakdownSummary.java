package by.skyperdyay.engine.analytics.middleware.model.response;

import java.math.BigDecimal;
import java.util.List;

public record CategoryBreakdownSummary(
        String mainCurrencyCode,
        BigDecimal totalIncomeAmount,
        BigDecimal totalExpenseAmount,
        List<CategoryBreakdownItem> incomeBreakdownSummary,
        List<CategoryBreakdownItem> expenseBreakdownSummary
) {
}
