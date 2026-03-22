package by.skyperdyay.engine.analytics.middleware.model.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoryBreakdownItem(
        UUID categoryId,
        String categoryName,
        BigDecimal totalAmount,
        BigDecimal percent
) {
}
