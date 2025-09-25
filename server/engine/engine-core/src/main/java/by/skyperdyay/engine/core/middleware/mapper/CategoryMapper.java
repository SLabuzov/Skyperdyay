package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.middleware.model.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "id", target = "categoryId")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "type", target = "categoryType")
    @Mapping(source = "icon", target = "categoryIcon")
    CategoryResponse convert(Category category);
}
