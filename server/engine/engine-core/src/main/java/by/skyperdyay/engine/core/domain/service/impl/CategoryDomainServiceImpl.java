package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.model.CategoryType;
import by.skyperdyay.engine.core.domain.repository.CategoryRepository;
import by.skyperdyay.engine.core.domain.service.CategoryDomainService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CategoryDomainServiceImpl implements CategoryDomainService {

    private final CategoryRepository categoryRepository;

    public CategoryDomainServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void defineCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> fetchAllUserCategories(String owner) {
        return categoryRepository.findAllByOwner(owner);
    }

    @Override
    public Category fetchIncomeUserCategory(UUID id, String owner) {
        return categoryRepository
                .findByIdAndOwnerAndType(id, owner, CategoryType.INCOME)
                .orElseThrow();
    }

    @Override
    public Category fetchExpenseUserCategory(UUID id, String owner) {
        return categoryRepository
                .findByIdAndOwnerAndType(id, owner, CategoryType.EXPENSE)
                .orElseThrow();
    }
}
