package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.model.CategoryType;
import by.skyperdyay.engine.core.domain.service.CategoryDomainService;
import by.skyperdyay.engine.core.middleware.mapper.CategoryMapper;
import by.skyperdyay.engine.core.middleware.model.request.DefineCategoryRequest;
import by.skyperdyay.engine.core.middleware.model.response.CategoryResponse;
import by.skyperdyay.engine.core.middleware.service.CategoryEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryFacade implements CategoryEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final CategoryDomainService categoryDomainService;
    private final CategoryMapper categoryMapper;

    public CategoryFacade(CurrentUserApiService currentUserApiService,
                          CategoryDomainService categoryDomainService,
                          CategoryMapper categoryMapper) {
        this.currentUserApiService = currentUserApiService;
        this.categoryDomainService = categoryDomainService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void defineCategory(DefineCategoryRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();
        CategoryType categoryType = CategoryType.valueOf(request.type());

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
                .map(categoryMapper::convert)
                .toList();
    }
}
