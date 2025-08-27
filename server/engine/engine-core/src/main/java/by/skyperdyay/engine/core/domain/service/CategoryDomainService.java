package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Category;

import java.util.List;

public interface CategoryDomainService {
    void defineCategory(Category category);

    List<Category> fetchAllUserCategories(String owner);
}
