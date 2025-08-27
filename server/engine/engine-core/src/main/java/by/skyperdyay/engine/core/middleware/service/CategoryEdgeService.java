package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.DefineCategoryRequest;
import by.skyperdyay.engine.core.middleware.model.response.CategoryResponse;

import java.util.List;

public interface CategoryEdgeService {
    void defineCategory(DefineCategoryRequest request);

    List<CategoryResponse> availableUserCategories();
}
