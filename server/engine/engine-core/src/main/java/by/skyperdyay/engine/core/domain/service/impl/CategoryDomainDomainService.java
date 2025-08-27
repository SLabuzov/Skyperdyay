package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.repository.CategoryRepository;
import by.skyperdyay.engine.core.domain.service.CategoryDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDomainDomainService implements CategoryDomainService {

    private final CategoryRepository categoryRepository;

    public CategoryDomainDomainService(CategoryRepository categoryRepository) {
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
}
