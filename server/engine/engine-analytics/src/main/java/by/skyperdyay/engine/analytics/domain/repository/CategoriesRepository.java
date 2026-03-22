package by.skyperdyay.engine.analytics.domain.repository;

import by.skyperdyay.engine.analytics.domain.model.CategoryBreakdown;
import java.time.LocalDate;
import java.util.List;

public interface CategoriesRepository {
    List<CategoryBreakdown> categoriesBreakdown(String owner, LocalDate startDate, LocalDate endDate);
}
