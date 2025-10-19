package by.skyperdyay.engine.analytics.web;

import by.skyperdyay.engine.analytics.middleware.model.request.PeriodRequest;
import by.skyperdyay.engine.analytics.middleware.model.response.CashFlowSummaryResponse;
import by.skyperdyay.engine.analytics.middleware.service.CashFlowSummaryEdgeService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics/cash-flow")
public class CashFlowResource {

    private final CashFlowSummaryEdgeService cashFlowSummaryEdgeService;

    public CashFlowResource(CashFlowSummaryEdgeService cashFlowSummaryEdgeService) {
        this.cashFlowSummaryEdgeService = cashFlowSummaryEdgeService;
    }

    @GetMapping
    List<CashFlowSummaryResponse> cashFlowSummary(@RequestParam("startDate") LocalDate begin, @RequestParam("endDate") LocalDate end) {
        PeriodRequest request = new PeriodRequest(begin, end);
        return cashFlowSummaryEdgeService.extractCashFlowSummaryByPeriod(request);
    }
}
