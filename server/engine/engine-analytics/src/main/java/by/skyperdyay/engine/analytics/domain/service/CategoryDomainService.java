package by.skyperdyay.engine.analytics.domain.service;

import by.skyperdyay.engine.analytics.domain.model.CategoryBreakdown;
import java.time.LocalDate;
import java.util.List;

public interface CategoryDomainService {
    List<CategoryBreakdown> generateCategoryBreakdownReport(String owner, LocalDate reportDate, int periodInterval);
}
