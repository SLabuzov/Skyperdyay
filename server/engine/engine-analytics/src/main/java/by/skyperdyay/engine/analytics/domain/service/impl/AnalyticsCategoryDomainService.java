package by.skyperdyay.engine.analytics.domain.service.impl;

import by.skyperdyay.engine.analytics.domain.model.CategoryBreakdown;
import by.skyperdyay.engine.analytics.domain.repository.CategoriesRepository;
import by.skyperdyay.engine.analytics.domain.service.CategoryDomainService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsCategoryDomainService implements CategoryDomainService {

    private final CategoriesRepository categoriesRepository;

    public AnalyticsCategoryDomainService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<CategoryBreakdown> generateCategoryBreakdownReport(String owner, LocalDate reportDate, int periodInterval) {
        if (periodInterval <= 1) {
            throw new IllegalArgumentException();
        }
        return categoriesRepository.categoriesBreakdown(owner, reportDate.minusDays(periodInterval), reportDate);
    }
}
