package by.skyperdyay.engine.analytics.domain.service;

import by.skyperdyay.engine.analytics.domain.model.BalanceTrend;
import java.time.LocalDate;
import java.util.List;

public interface BalanceDomainService {
    List<BalanceTrend> generateTrendingReport(String owner, LocalDate reportDate, int periodInterval);
}
