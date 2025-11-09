package by.skyperdyay.engine.analytics.middleware.model.response;

import java.util.List;

public record FinancialDashboard(
        List<CurrencyFinancialSummary> financialReports
) {
}
