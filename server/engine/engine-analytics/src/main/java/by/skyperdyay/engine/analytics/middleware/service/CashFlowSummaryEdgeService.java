package by.skyperdyay.engine.analytics.middleware.service;

import by.skyperdyay.engine.analytics.middleware.model.request.PeriodRequest;
import by.skyperdyay.engine.analytics.middleware.model.response.CashFlowSummaryResponse;
import java.util.List;

public interface CashFlowSummaryEdgeService {
    List<CashFlowSummaryResponse> extractCashFlowSummaryByPeriod(PeriodRequest request);
}
