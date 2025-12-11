package by.skyperdyay.engine.analytics.middleware.service.impl;

import by.skyperdyay.engine.analytics.domain.model.BalanceTrend;
import by.skyperdyay.engine.analytics.domain.service.BalanceDomainService;
import by.skyperdyay.engine.analytics.middleware.model.response.BalancePeriodMetrics;
import by.skyperdyay.engine.analytics.middleware.model.response.CurrencyFinancialSummary;
import by.skyperdyay.engine.analytics.middleware.model.response.FinancialDashboard;
import by.skyperdyay.engine.analytics.middleware.service.FinancialDashboardEdgeService;
import by.skyperdyay.engine.analytics.middleware.validator.FinancialDashboardValidator;
import by.skyperdyay.security.api.CurrentUserApiService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FinancialDashboardFacade implements FinancialDashboardEdgeService {

    private final int BALANCE_TREND_INTERVAL = 30;

    private final BalanceDomainService balanceDomainService;
    private final CurrentUserApiService currentUserApiService;
    private final FinancialDashboardValidator financialDashboardValidator;

    public FinancialDashboardFacade(BalanceDomainService balanceDomainService,
                                    CurrentUserApiService currentUserApiService,
                                    FinancialDashboardValidator financialDashboardValidator) {
        this.balanceDomainService = balanceDomainService;
        this.currentUserApiService = currentUserApiService;
        this.financialDashboardValidator = financialDashboardValidator;
    }

    @Override
    public FinancialDashboard assembleFinancialOverview() {
        LocalDate reportDate = LocalDate.now();
        String owner = currentUserApiService.currentUserAccount().userId();

        financialDashboardValidator.validateBeforeExecution(owner);

        List<BalanceTrend> balanceTrends = balanceDomainService.generateTrendingReport(owner, reportDate, BALANCE_TREND_INTERVAL);

        // На данный момент как заглушка. Находим главную валюту по кол-ву операций за весь период.
        BalanceTrend mainCurrencyTrend = Collections
                .max(balanceTrends, Comparator.comparing(BalanceTrend::countTotalTransactions));

        List<CurrencyFinancialSummary> financialReports = balanceTrends
                .stream()
                .map(trendItem -> map(trendItem, mainCurrencyTrend))
                .toList();

        return new FinancialDashboard(financialReports);
    }

    private CurrencyFinancialSummary map(BalanceTrend trendItem, BalanceTrend mainCurrencyTrend) {
        boolean isMainCurrency = trendItem.equals(mainCurrencyTrend);

        // Чистый доход
        BigDecimal netIncomeBaseline = trendItem.baselineTotalIncome().subtract(trendItem.baselineTotalExpense());
        BigDecimal netIncomeCurrent = trendItem.currentTotalIncome().subtract(trendItem.currentTotalExpense());

        // Разница на периоде
        BigDecimal incomeDiff = trendItem.currentTotalIncome().subtract(trendItem.baselineTotalIncome());
        BigDecimal expenseDiff = trendItem.currentTotalExpense().subtract(trendItem.baselineTotalExpense());
        BigDecimal netIncomeDiff = netIncomeCurrent.subtract(netIncomeBaseline);

        // Разница на периоде в процентном соотношении
        BigDecimal incomePercentage = calculatePercentage(trendItem.baselineTotalIncome(), trendItem.currentTotalIncome());
        BigDecimal expensePercentage = calculatePercentage(trendItem.baselineTotalExpense(), trendItem.currentTotalExpense());
        BigDecimal netIncomePercentage = calculatePercentage(netIncomeBaseline, netIncomeCurrent);

        BalancePeriodMetrics balancePeriodMetrics = new BalancePeriodMetrics(
                BALANCE_TREND_INTERVAL,
                trendItem.currentTotalIncome(),
                trendItem.currentTotalExpense(),
                netIncomeCurrent,
                incomeDiff,
                expenseDiff,
                netIncomeDiff,
                incomePercentage,
                expensePercentage,
                netIncomePercentage
        );

        return new CurrencyFinancialSummary(
                isMainCurrency,
                trendItem.currencyCode(),
                trendItem.currentBalance(),
                balancePeriodMetrics
        );
    }

    private BigDecimal calculatePercentage(BigDecimal oldValue, BigDecimal newValue) {
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        boolean oldIsZero = oldValue.compareTo(BigDecimal.ZERO) == 0;
        boolean newIsZero = newValue.compareTo(BigDecimal.ZERO) == 0;

        // OLD ноль, NEW ноль → +0%
        if (oldIsZero && newIsZero) {
            return BigDecimal.ZERO.setScale(scale, roundingMode);
        }

        // OLD ноль, NEW не ноль → +100%
        if (oldIsZero) {
            return BigDecimal.valueOf(100).setScale(scale, roundingMode);
        }

        // NEW ноль, OLD не ноль → -100%
        if (newIsZero) {
            return BigDecimal.valueOf(-100).setScale(scale, roundingMode);
        }

        // Вычисление процента: ((newValue - oldValue) / oldValue) * 100
        BigDecimal diff = newValue.subtract(oldValue);
        BigDecimal quotient = diff.divide(oldValue, scale + 6, RoundingMode.HALF_UP);
        BigDecimal percentage = quotient.multiply(BigDecimal.valueOf(100));

        return percentage.setScale(scale, roundingMode);
    }
}
