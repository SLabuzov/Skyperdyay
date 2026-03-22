package by.skyperdyay.engine.analytics.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoryBreakdown(
        UUID categoryId,
        String categoryName,
        String categoryType,
        String mainCurrencyCode,
        BigDecimal totalAmount,
        BigDecimal percent
) {
}
