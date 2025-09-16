package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Category;
import java.util.List;
import java.util.UUID;

public interface CategoryDomainService {
    void defineCategory(Category category);

    List<Category> fetchAllUserCategories(String owner);

    Category fetchIncomeUserCategory(UUID id, String owner);

    Category fetchExpenseUserCategory(UUID id, String owner);
}
