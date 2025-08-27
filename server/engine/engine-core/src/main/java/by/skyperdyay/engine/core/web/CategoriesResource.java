package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.DefineCategoryRequest;
import by.skyperdyay.engine.core.middleware.model.response.CategoryResponse;
import by.skyperdyay.engine.core.middleware.service.CategoryEdgeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesResource {

    private final CategoryEdgeService categoryEdgeService;

    public CategoriesResource(CategoryEdgeService categoryEdgeService) {
        this.categoryEdgeService = categoryEdgeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void defineCategory(@RequestBody DefineCategoryRequest request) {
        categoryEdgeService.defineCategory(request);
    }

    @GetMapping
    List<CategoryResponse> availableUserCategories() {
        return categoryEdgeService.availableUserCategories();
    }
}
