package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.model.CategoryType;
import by.skyperdyay.engine.core.domain.service.CategoryDomainService;
import by.skyperdyay.engine.core.middleware.model.request.DefineCategoryRequest;
import by.skyperdyay.engine.core.middleware.model.response.CategoryResponse;
import by.skyperdyay.engine.core.middleware.service.CategoryEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryFacade implements CategoryEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final CategoryDomainService categoryDomainService;

    public CategoryFacade(CurrentUserApiService currentUserApiService,
                          CategoryDomainService categoryDomainService) {
        this.currentUserApiService = currentUserApiService;
        this.categoryDomainService = categoryDomainService;
    }

    @Override
    public void defineCategory(DefineCategoryRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();
        CategoryType categoryType = CategoryType.valueOf(request.name());

        Category category = new Category();
        category.setOwner(owner);
        category.setType(categoryType);
        category.setName(request.name());
        category.setIcon(request.icon());

        categoryDomainService.defineCategory(category);
    }

    @Override
    public List<CategoryResponse> availableUserCategories() {
        String owner = currentUserApiService.currentUserAccount().userId();

        return categoryDomainService.fetchAllUserCategories(owner)
                .stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getType().name(),
                        category.getIcon()
                ))
                .toList();
    }
}
