package by.skyperdyay.engine.core.middleware.model.response;

import java.util.UUID;

public record CategoryResponse(
        UUID categoryId,
        String categoryName,
        String categoryType,
        String categoryIcon
) {
}
