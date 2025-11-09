package by.skyperdyay.engine.analytics.web;

import by.skyperdyay.engine.analytics.middleware.model.response.FinancialDashboard;
import by.skyperdyay.engine.analytics.middleware.service.FinancialDashboardEdgeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financial-analytics")
public class FinancialAnalyticsResource {

    private final FinancialDashboardEdgeService financialDashboardEdgeService;

    public FinancialAnalyticsResource(FinancialDashboardEdgeService financialDashboardEdgeService) {
        this.financialDashboardEdgeService = financialDashboardEdgeService;
    }

    @GetMapping("/dashboard")
    FinancialDashboard generateDashboardInfo() {
        return financialDashboardEdgeService.assembleFinancialOverview();
    }
}
