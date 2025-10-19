package by.skyperdyay.engine.analytics.domain.service;

import by.skyperdyay.engine.analytics.domain.model.CashFlowView;
import java.time.LocalDate;
import java.util.List;

public interface CashFlowDomainService {

    List<CashFlowView> aggregateOwnerCashFlowMonthly(String owner, LocalDate startPeriod, LocalDate endPeriod);
}
