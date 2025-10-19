package by.skyperdyay.engine.analytics.middleware.service.impl;

import by.skyperdyay.engine.analytics.domain.model.CashFlowView;
import by.skyperdyay.engine.analytics.domain.service.CashFlowDomainService;
import by.skyperdyay.engine.analytics.middleware.model.request.PeriodRequest;
import by.skyperdyay.engine.analytics.middleware.model.response.CashFlowSummaryResponse;
import by.skyperdyay.engine.analytics.middleware.service.CashFlowSummaryEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class CashFlowSummaryFacade implements CashFlowSummaryEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final CashFlowDomainService cashFlowDomainService;

    public CashFlowSummaryFacade(CurrentUserApiService currentUserApiService,
                                 CashFlowDomainService cashFlowDomainService) {
        this.currentUserApiService = currentUserApiService;
        this.cashFlowDomainService = cashFlowDomainService;
    }

    @Override
    public List<CashFlowSummaryResponse> extractCashFlowSummaryByPeriod(PeriodRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();

        List<CashFlowView> cashFlowViews = cashFlowDomainService.aggregateOwnerCashFlowMonthly(
                owner,
                request.startPeriod(),
                request.endPeriod()
        );



        return cashFlowViews.stream()
                .map(it -> new CashFlowSummaryResponse(
                                it.incomeAmount(),
                                it.expenseAmount(),
                                it.month(),
                                it.currencyCode()
                        )
                )
                .toList();
    }

    private List<LocalDate> generateMonthlyDates(LocalDate start, LocalDate end) {
        return Stream.iterate(start, date -> date.plusMonths(1))
                .limit(1 + end.getMonthValue() - start.getMonthValue() +
                        12 * (end.getYear() - start.getYear()))
                .filter(date -> !date.isAfter(end))
                .collect(Collectors.toList());
    }
}
