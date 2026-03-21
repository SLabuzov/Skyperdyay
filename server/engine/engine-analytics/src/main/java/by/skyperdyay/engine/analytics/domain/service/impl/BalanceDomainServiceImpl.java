package by.skyperdyay.engine.analytics.domain.service.impl;

import by.skyperdyay.engine.analytics.domain.model.BalanceTrend;
import by.skyperdyay.engine.analytics.domain.model.PeriodMetrics;
import by.skyperdyay.engine.analytics.domain.model.TotalBalance;
import by.skyperdyay.engine.analytics.domain.repository.PeriodMetricsRepository;
import by.skyperdyay.engine.analytics.domain.repository.TotalBalanceRepository;
import by.skyperdyay.engine.analytics.domain.service.BalanceDomainService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BalanceDomainServiceImpl implements BalanceDomainService {

    private final TotalBalanceRepository totalBalanceRepository;
    private final PeriodMetricsRepository periodMetricsRepository;

    public BalanceDomainServiceImpl(TotalBalanceRepository totalBalanceRepository,
                                    PeriodMetricsRepository periodMetricsRepository) {
        this.totalBalanceRepository = totalBalanceRepository;
        this.periodMetricsRepository = periodMetricsRepository;
    }


    @Override
    public List<BalanceTrend> generateTrendingReport(String owner, LocalDate reportDate, int periodInterval) {
        if (periodInterval <= 1) {
            throw new IllegalArgumentException();
        }
        List<TotalBalance> totalBalances = totalBalanceRepository.calculateTotalBalance(owner, reportDate);
        List<PeriodMetrics> periodMetrics = periodMetricsRepository.generatePeriodOverPeriodMetrics(owner, periodInterval, reportDate);

        List<String> availableCurrencies = totalBalances.stream().map(TotalBalance::currencyCode).toList();


        List<BalanceTrend> balanceTrendResult = new ArrayList<>(availableCurrencies.size());

        for (String currencyCode : availableCurrencies) {
            TotalBalance currentTotalBalance = totalBalances
                    .stream()
                    .filter(it -> it.currencyCode().equals(currencyCode))
                    .findFirst()
                    .orElseThrow();

            PeriodMetrics currentPeriodMetrics = periodMetrics
                    .stream()
                    .filter(it -> it.currencyCode().equals(currencyCode))
                    .findFirst()
                    .orElseThrow();

            BalanceTrend currentBalanceTrend = new BalanceTrend(
                    currentTotalBalance.currencyCode(),
                    currentTotalBalance.mainCurrencyCode(),
                    currentTotalBalance.totalBalance(),
                    currentTotalBalance.totalBalanceMcc(),
                    currentPeriodMetrics.baselineTotalIncome(),
                    currentPeriodMetrics.baselineTotalIncomeMcc(),
                    currentPeriodMetrics.baselineTotalExpense(),
                    currentPeriodMetrics.baselineTotalExpenseMcc(),
                    currentPeriodMetrics.currentTotalIncome(),
                    currentPeriodMetrics.currentTotalIncomeMcc(),
                    currentPeriodMetrics.currentTotalExpense(),
                    currentPeriodMetrics.currentTotalExpenseMcc(),
                    currentPeriodMetrics.countBaselineTransactions(),
                    currentPeriodMetrics.countCurrentTransactions(),
                    currentPeriodMetrics.countTotalTransactions()
            );

            balanceTrendResult.add(currentBalanceTrend);
        }
        return balanceTrendResult;
    }
}
