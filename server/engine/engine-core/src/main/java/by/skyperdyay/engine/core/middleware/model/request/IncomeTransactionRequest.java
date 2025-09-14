package by.skyperdyay.engine.core.middleware.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncomeTransactionRequest(
        UUID walletId,
        UUID incomeCategoryId,
        BigDecimal amount,
        LocalDate transactionDate,
        String notes
) {
}
