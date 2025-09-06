package by.skyperdyay.engine.core.middleware.model.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record IncomeTransactionRequest(
        UUID walletId,
        UUID incomeCategoryId,
        BigDecimal amount,
        Date transactionDate,
        String notes
) {
}
