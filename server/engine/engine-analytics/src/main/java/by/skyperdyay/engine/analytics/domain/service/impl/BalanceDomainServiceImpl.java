package by.skyperdyay.engine.analytics.domain.service.impl;

import by.skyperdyay.engine.analytics.domain.model.BalanceTrend;
import by.skyperdyay.engine.analytics.domain.repository.BalanceTrendingRepository;
import by.skyperdyay.engine.analytics.domain.service.BalanceDomainService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BalanceDomainServiceImpl implements BalanceDomainService {

    private final BalanceTrendingRepository balanceTrendingRepository;

    public BalanceDomainServiceImpl(BalanceTrendingRepository balanceTrendingRepository) {
        this.balanceTrendingRepository = balanceTrendingRepository;
    }

    @Override
    public List<BalanceTrend> generateTrendingReport(String owner, LocalDate reportDate, int periodInterval) {
        if (periodInterval <= 1) {
            throw new IllegalArgumentException();
        }
        return balanceTrendingRepository.generatePeriodOverPeriodChangeReport(owner, periodInterval, reportDate);
    }
}
